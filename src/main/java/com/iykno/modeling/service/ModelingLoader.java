package com.iykno.modeling.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.iykno.modeling.bean.ModelingConfig;
import com.iykno.modeling.bean.ModelingReload;
import com.iykno.modeling.bean.TableModel;
import com.iykno.modeling.cache.CacheManager;
import com.iykno.modeling.common.CollectionUtils;
import com.iykno.modeling.common.CommonConstants;
import com.iykno.modeling.common.SQLBuilder;
import com.iykno.modeling.config.ModelingConfigFactory;

/**
 * 
 * 建模数据加载\刷新
 * 
 * @author iykno
 *
 */
@Service
public class ModelingLoader {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

	protected final Set<String> loadingSet = new ConcurrentSkipListSet<String>();

	@Autowired
	BaseService baseService;

	/**
	 * load
	 * 
	 * @param c
	 */
	public void load(ModelingConfig c) {

		logger.info("ModelingLoader::load:ModelingConfig={}, startTime={}", c, new Date());
		long startTime = System.currentTimeMillis();

		/*
		 * 最终数据 key: DeviceModelingConfig.mainTable.mainColumn的值 value: ( key:
		 * TableModel.jsonKey || DeviceModelingConfig.jsonKey value:
		 * 满足TableModel.slaveTable.mappingColumn ==
		 * DeviceModelingConfig.mainTable.mainColumn 的筛选数据
		 * 
		 * )
		 */
		Map<String, ConcurrentHashMap<String, List<Map<String, Object>>>> data = new ConcurrentHashMap<String, ConcurrentHashMap<String, List<Map<String, Object>>>>();

		// 查数据放入data
		load(data, c, null);

		// data入缓存
		CacheManager.setData(CommonConstants.MODELING_CACHE_PREFIX + c.getTopic(), data, 0);

		// 开启刷新任务
		startReloadScheduled(c);

		logger.info("ModelingLoader:load::endTime={}, used={}", new Date(), System.currentTimeMillis() - startTime);
	}

	/**
	 * 
	 * 
	 * @param data
	 *            更新的目标数据
	 * @param c
	 *            配置
	 * @param jsonKeys
	 *            更新哪些jsonKey
	 */
	private void load(Map<String, ConcurrentHashMap<String, List<Map<String, Object>>>> data, ModelingConfig c,
			List<String> jsonKeys) {
		// 查询所有主表数据
		List<Map<String, Object>> mainList = getMainList(c);
		List<String> mainColumnList = CollectionUtils.getValListByKey(c.getMainColumn(), mainList);

		// 过滤掉在数据库已删除数据
		Set<String> keySet = data.keySet();
		for (String key : keySet) {
			if (!mainColumnList.contains(key)) {
				data.remove(key);
			}
		}

		if (!mainColumnList.isEmpty()) {
			final int lstSize = mainColumnList.size();
			int insertSize = CommonConstants.batch_insert_size;
			if (lstSize > 0) {
				int toIndex = 0;

				// 分批加载
				for (int j = 0; j < lstSize; j = j + insertSize) {
					toIndex = Math.min(j + insertSize, lstSize);
					List<String> subList = mainColumnList.subList(j, toIndex);

					loadData(data, c.getTopic(), subList, jsonKeys);

				}
			}
		}
	}

	/**
	 * 
	 * @param modelingReload
	 */
	public void reload(ModelingReload modelingReload) {

		logger.info("ModelingLoader reload ..modelingReload {}", modelingReload);

		if (modelingReload != null && modelingReload.getTopic() != null) {

			if (loadingSet.contains(modelingReload.getTopic())) {
				logger.info("ModelingLoader reload topic:{} is running..", modelingReload.getTopic());
			} else {
				// 加逻辑锁避免不必要刷新
				boolean lock = needLock(modelingReload);
				if (lock) {
					loadingSet.add(modelingReload.getTopic());
				}
				boolean mainValFlag = isNullOrEmpty(modelingReload.getMainColumnVals());
				try {
					if (mainValFlag) {
						load(modelingReload.getTopic(), modelingReload.getJsonKeys());

					} else {
						load(modelingReload.getTopic(), modelingReload.getMainColumnVals(),
								modelingReload.getJsonKeys());
					}
				} finally {
					if (lock) {
						loadingSet.remove(modelingReload.getTopic());
					}
				}
			}
		} else {
			logger.info("ModelingLoader reload failed ..");
		}
	}

	/**
	 * 根据刷新的程度 判断是否加锁
	 * 
	 * @param m
	 * @return
	 */
	private boolean needLock(ModelingReload m) {
		return isNullOrEmpty(m.getJsonKeys()) && isNullOrEmpty(m.getMainColumnVals());
	}

	private static boolean isNullOrEmpty(Object o) {
		return o == null || String.valueOf(o).trim().length() == 0 || String.valueOf(o).trim().equals("null");
	}

	/**
	 * 加载或刷新 部分数据
	 * 
	 * @param topic
	 * @param jsonKeys
	 */
	private void load(String topic, List<String> jsonKeys) {
		ModelingConfig c = ModelingConfigFactory.getInstance().getConfigByTopic(topic);
		Map<String, ConcurrentHashMap<String, List<Map<String, Object>>>> data = CacheManager
				.getData(CommonConstants.MODELING_CACHE_PREFIX + topic);

		if (c != null && data != null) {
			load(data, c, jsonKeys);
		} else {
			logger.error("ModelingLoader::load: topic={} 不存在", topic);
		}
	}

	/**
	 * 加载或刷新 部分数据
	 * 
	 * @param topic
	 * @param mainColumnVals
	 * @param jsonKeys
	 */
	private void load(String topic, List<String> mainColumnVals, List<String> jsonKeys) {

		ModelingConfig c = ModelingConfigFactory.getInstance().getConfigByTopic(topic);

		Map<String, ConcurrentHashMap<String, List<Map<String, Object>>>> data = CacheManager
				.getData(CommonConstants.MODELING_CACHE_PREFIX + topic);

		if (c != null && data != null) {

			loadData(data, topic, mainColumnVals, jsonKeys);

		} else {
			logger.error("ModelingLoader::load: topic={} 不存在", topic);
		}

	}

	/**
	 * 
	 * @param data
	 * @param topic
	 * @param mainColumnVals
	 * @param jsonKeys
	 */
	private void loadData(Map<String, ConcurrentHashMap<String, List<Map<String, Object>>>> data, String topic,
			List<String> mainColumnVals, List<String> jsonKeys) {

		ModelingConfig c = ModelingConfigFactory.getInstance().getConfigByTopic(topic);

		if (needReload(topic, jsonKeys)) {
			// 加载主数据
			addAsMain(data, mainColumnVals, c);
		}

		// 循环从表
		for (TableModel table : c.getTables()) {

			if (needReload(table.getJsonKey(), jsonKeys)) {
				// 没有配置映射字段,直接使用主字段
				String slaveColumn = StringUtils.isEmpty(table.getMappingColumn()) ? c.getMainColumn()
						: table.getMappingColumn();
				// 查询从数据
				List<Map<String, Object>> slaveSubList = getSlaveSubList(slaveColumn, table,
						CollectionUtils.getValConcat(mainColumnVals));
				// 加载从数据
				addAsSlave(data, slaveSubList, slaveColumn, table.getJsonKey());
			}

		}
	}

	/**
	 * 判断是否需要重新加载
	 * 
	 * @param jsonKey
	 * @param jsonKeys
	 * @return
	 */
	private boolean needReload(String jsonKey, List<String> jsonKeys) {
		return jsonKeys == null || jsonKeys.isEmpty() || jsonKeys.contains(jsonKey);
	}

	private void addAsMain(Map<String, ConcurrentHashMap<String, List<Map<String, Object>>>> deviceMap,
			List<String> mainColumnVals, ModelingConfig c) {

		logger.info("ModelingLoader:addAsMain::mainColumn={} ", c.getMainColumn());

		List<Map<String, Object>> mainList = baseService.getResultListBySQL(SQLBuilder.buildMain(c, mainColumnVals));
		for (Map<String, Object> map : mainList) {
			String keyVal = MapUtils.getString(map, c.getMainColumn());
			deviceMap.put(keyVal, new ConcurrentHashMap<String, List<Map<String, Object>>>());
		}

		this.addAsSlave(deviceMap, mainList, c.getMainColumn(), c.getTopic());

	}

	/**
	 * 
	 * @param column
	 * @param table
	 * @param columnVals
	 * @return
	 */
	private List<Map<String, Object>> getSlaveSubList(String column, TableModel table, String columnVals) {
		logger.info("ModelingLoader:getSlaveSubList::slaveTable={} ", table.getSlaveTable());

		return baseService.getResultListBySQLGroup(SQLBuilder.buildSlave(column, table, columnVals));
	}

	/**
	 * 从表数据加载
	 * 
	 * @param deviceMap
	 * @param slaveSubList
	 * @param slaveColumn
	 * @param jsonKey
	 */
	private void addAsSlave(Map<String, ConcurrentHashMap<String, List<Map<String, Object>>>> deviceMap,
			List<Map<String, Object>> slaveSubList, String slaveColumn, String jsonKey) {
		logger.info("ModelingLoader:addAsSlave::slaveColumn={} ", slaveColumn);
		Map<String, List<Map<String, Object>>> groupListByCond = CollectionUtils.groupListByCond(slaveColumn,
				slaveSubList);

		Set<String> keySet = groupListByCond.keySet();

		for (String key : keySet) {

			// 正常是已经存在
			if (!deviceMap.containsKey(key)) {
				deviceMap.put(key, new ConcurrentHashMap<String, List<Map<String, Object>>>());
			}

			deviceMap.get(key).put(jsonKey, groupListByCond.get(key));
		}

	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	private List<Map<String, Object>> getMainList(ModelingConfig c) {
		logger.info("ModelingLoader:getMainList::param={}", c);

		List<Map<String, Object>> mainList = new ArrayList<Map<String, Object>>();
		int batchSize = CommonConstants.batch_select_size;
		int maxId = getMaxId(c.getMainTable(), c.getMainColumn());
		int curId = 0;
		while (curId < maxId) {
			long beginTime = System.currentTimeMillis();
			try {
				List<Map<String, Object>> curList = baseService
						.getResultListBySQL(SQLBuilder.buildMain(c, curId, curId + batchSize));
				if (!curList.isEmpty()) {
					mainList.addAll(curList);
				}
			} catch (Exception e) {
				logger.error("getMainList::{}", e);
			} finally {
				curId += batchSize;
				logger.debug("ModelingLoader:getMainList::curId={} used={}", curId,
						System.currentTimeMillis() - beginTime);
			}

		}

		return mainList;
	}

	/**
	 * 
	 * @param mainTable
	 * @param mainColumn
	 * @return
	 */
	private int getMaxId(String mainTable, String mainColumn) {
		logger.info("ModelingLoader:getMaxId::param={} {}", mainTable, mainColumn);
		StringBuffer sGetSQL = new StringBuffer();
		sGetSQL.append("select ifnull(max(").append(mainColumn).append("),0) as maxId from ").append(mainTable);
		Map<String, Object> resultMap = baseService.getResultMapBySQL(sGetSQL.toString());
		return MapUtils.getIntValue(resultMap, "maxId", 0);
	}

	/**
	 * 开启重新加载/刷新任务
	 * 
	 * @param c
	 */
	private void startReloadScheduled(ModelingConfig c) {

		if (c.getPeriod() != null && c.getPeriod() > 0) {
			scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					logger.info("ModelingLoader:startReloadScheduled::" + new Date());
					if (loadingSet.add(c.getTopic())) {
						try {
							reload(new ModelingReload(c.getTopic(), null));
						} finally {
							loadingSet.remove(c.getTopic());
							logger.info("ModelingLoader:startReloadScheduled loadingSet.remove..topic: {} ..",
									c.getTopic());
						}

					}

				}
			}, 1, c.getPeriod(), TimeUnit.SECONDS);
		}

		for (TableModel table : c.getTables()) {
			if (table.getPeriod() != null && table.getPeriod() > 0) {
				scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
					@Override
					public void run() {
						logger.info("ModelingLoader:startReloadScheduled::" + new Date());
						if (!loadingSet.contains(c.getTopic())) {
							reload(new ModelingReload(c.getTopic(), table.getJsonKey()));
						} else {
							logger.info("ModelingLoader:startReloadScheduled ..topic: {} is runnig..", c.getTopic());
						}
					}
				}, 1, table.getPeriod(), TimeUnit.SECONDS);
			}
		}
	}
}
