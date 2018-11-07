package com.iykno.modeling.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.druid.util.StringUtils;
import com.iykno.modeling.bean.ModelingCondition;
import com.iykno.modeling.cache.CacheManager;
import com.iykno.modeling.common.CollectionUtils;
import com.iykno.modeling.common.CommonConstants;

/**
 * 
 * @author iykno
 *
 */
public class ModelingSelector {

	/**
	 * 根据筛选条件过滤数据，获得满足条件的数据
	 * 
	 * @param topic
	 * @param conditionList
	 * @return
	 */
	public static List<Map<String, List<Map<String, Object>>>> getSatisfiedByCond(String topic,
			List<ModelingCondition> conditionList) {
		List<Map<String, List<Map<String, Object>>>> result = new ArrayList<Map<String, List<Map<String, Object>>>>();
		Map<String, Map<String, List<Map<String, Object>>>> data = CacheManager
				.getData(CommonConstants.MODELING_CACHE_PREFIX + topic);

		if (data != null) {
			Set<String> keySet = data.keySet();
			for (String key : keySet) {
				Map<String, List<Map<String, Object>>> val = data.get(key);
				if (check(val, conditionList)) {
					result.add(val);
				}
			}
		}

		return result;
	}

	/**
	 * 判断行数据是否符合检索条件组
	 * 
	 * @param val
	 *            行数据
	 * @param conditionList
	 *            条件组合
	 * @return
	 */
	public static boolean check(Map<String, List<Map<String, Object>>> val, List<ModelingCondition> conditionList) {
		boolean result = true;
		if (conditionList != null && !conditionList.isEmpty()) {
			for (ModelingCondition condition : conditionList) {
				result = result && check(val, condition);
			}
		}
		return result;
	}

	/**
	 * 判断行数据是否符合检索条件
	 * 
	 * @param val
	 *            行数据
	 * @param condition
	 *            条件
	 * @return
	 */
	public static boolean check(Map<String, List<Map<String, Object>>> val, ModelingCondition condition) {
		boolean result = false;
		if (condition != null) {
			if (!StringUtils.isEmpty(condition.getCondition())) {
				String[] arr = condition.getCondition().split(CommonConstants.operator_point);
				// 目前条件层次设定为2层结构、后续的后续再说
				if (arr.length == 2) {
					List<Map<String, Object>> list = val.get(arr[0]);
					Set<String> valSetByKey = CollectionUtils.getValSetByKey(arr[1], list);
					result = valSetByKey.contains(condition.getConditionVal());
				}
			}
		}

		return result;
	}
}
