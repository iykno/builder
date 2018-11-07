package com.iykno.modeling.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iykno.modeling.cache.CacheManager;
import com.iykno.modeling.mapper.BaseMapper;

/**
 * 基础通用服务：
 * 
 * @author iykno
 *
 */
@Service
public class BaseService {

	public final static Logger logger = LoggerFactory.getLogger(BaseService.class);

	@Autowired
	BaseMapper baseMapper;

	public List<Map<String, Object>> getResultListBySQLGroup(List<String> sqlGroup) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (String sql : sqlGroup) {
			resultList.addAll(this.getResultListBySQL(sql));
		}
		return resultList;
	}

	public List<Map<String, Object>> getResultListBySQL(String sql) {
		return baseMapper.queryDataList(sql);
	}

	public List<Map<String, Object>> getResultListBySQL(String sql, int expire) {
		// 使用hash值做key 暂不考虑冲突
		String cacheKey = String.valueOf(sql.hashCode());

		List<Map<String, Object>> resultList = CacheManager.getData(cacheKey);

		if (resultList == null) {
			resultList = baseMapper.queryDataList(sql);
			CacheManager.setData(cacheKey, resultList, expire);
		}

		return resultList;
	}

	public Map<String, Object> getResultMapBySQL(String sql) {
		return baseMapper.queryDataMap(sql);
	}

	public Map<String, Object> getResultMapBySQL(String sql, int expire) {
		// 使用hash值做key 暂不考虑冲突
		String cacheKey = String.valueOf(sql.hashCode());

		Map<String, Object> resultMap = CacheManager.getData(cacheKey);

		if (resultMap == null) {
			resultMap = baseMapper.queryDataMap(sql);
			CacheManager.setData(cacheKey, resultMap, expire);
		}

		return resultMap;
	}

}
