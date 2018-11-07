package com.iykno.modeling.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iykno.modeling.bean.ModelingConditionGroup;
import com.iykno.modeling.cache.CacheManager;
import com.iykno.modeling.common.CommonConstants;
import com.iykno.modeling.common.ListPageUtil;
import com.iykno.modeling.common.Result;
import com.iykno.modeling.comparator.ModelingComparator;

/**
 * 建模服务：提供查询
 * 
 * @author iykno
 *
 */
@Service
public class ModelingSelectService {

	/**
	 * 查单条信息
	 * 
	 * @param index
	 * @param mainColumnVal
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> getResultById(String topic, String mainColumnVal) {
		Map<String, Map<String, List<Map<String, Object>>>> data = CacheManager
				.getData(CommonConstants.MODELING_CACHE_PREFIX + topic);
		return data.get(mainColumnVal);
	}

	/**
	 * 根据条件组合查询
	 * 
	 * @param conditionGroup
	 * @return
	 */
	public Result getListByCond(ModelingConditionGroup conditionGroup) {

		Result result = null;

		List<Map<String, List<Map<String, Object>>>> allList = ModelingSelector
				.getSatisfiedByCond(conditionGroup.getTopic(), conditionGroup.getConditionList());

		// 根据orderby条件进行排序
		Collections.sort(allList, new ModelingComparator(conditionGroup.getOrderByList()));

		// 借用下分页工具
		ListPageUtil pageUtil = new ListPageUtil(allList, conditionGroup.getPageSize());
		// 获取分页List数据
		List<?> sublist = pageUtil.getObjects(conditionGroup.getPageNo());

		// 封装返回数据
		result = new Result(conditionGroup.getPageNo(), allList.size(), conditionGroup.getPageSize());
		result.setData(sublist);

		return result;
	}

}
