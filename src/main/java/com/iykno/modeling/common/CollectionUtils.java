package com.iykno.modeling.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;

/**
 * 
 * @author iykno
 *
 */
public class CollectionUtils {

	/**
	 * 从mapList中找到map(key)==value的那条数据
	 * 
	 * @param key
	 * @param value
	 * @param mapList
	 * @return
	 */
	public static List<Map<String, Object>> getSingleSubListByCond(String key, String value,
			List<Map<String, Object>> mapList) {
		List<Map<String, Object>> subList = new ArrayList<Map<String, Object>>();
		if (key != null && value != null && mapList != null && !mapList.isEmpty()) {
			for (Map<String, Object> tempMap : mapList) {
				if (value.equals(String.valueOf(tempMap.get(key)))) {
					subList.add(tempMap);
					break;
				}
			}
		}
		return subList;
	}

	/**
	 * 从mapList中找到满足map(key)==value条件的子list
	 * 
	 * @param key
	 * @param value
	 * @param mapList
	 * @return
	 */
	public static List<Map<String, Object>> getSubListByCond(String key, String value,
			List<Map<String, Object>> mapList) {
		List<Map<String, Object>> subList = new ArrayList<Map<String, Object>>();
		if (key != null && value != null && mapList != null && !mapList.isEmpty()) {
			for (Map<String, Object> tempMap : mapList) {
				if (value.equals(String.valueOf(tempMap.get(key)))) {
					subList.add(tempMap);
				}
			}
		}
		return subList;
	}

	/**
	 * 从mapList中找到满足map(key)在valList中的子list
	 * 
	 * @param key
	 * @param valList
	 * @param mapList
	 * @return
	 */
	public static List<Map<String, Object>> getSubListByCond(String key, List<String> valList,
			List<Map<String, Object>> mapList) {
		List<Map<String, Object>> subList = new ArrayList<Map<String, Object>>();
		if (key != null && mapList != null && !mapList.isEmpty()) {
			for (Map<String, Object> tempMap : mapList) {
				if (valList.contains(String.valueOf(tempMap.get(key)))) {
					subList.add(tempMap);
				}
			}
		}
		return subList;
	}

	/**
	 * 从valLst中获得val的拼接串
	 * 
	 * @param valLst
	 * @return
	 */
	public static String getValConcat(List<?> valLst) {
		StringBuffer buffer = new StringBuffer();
		if (valLst != null && !valLst.isEmpty()) {
			for (Object val : valLst) {
				if (val != null) {
					buffer.append(val).append(",");
				}

			}
		}

		return buffer.length() > 0 ? buffer.substring(0, buffer.length() - 1).toString() : "";
	}

	/**
	 * 从mapLst中获得map(key)的拼接串
	 * 
	 * @param key
	 * @param mapLst
	 * @return
	 */
	public static String getValConcatByKeys(String key, List<Map<String, Object>> mapLst) {
		StringBuffer buffer = new StringBuffer();
		if (mapLst != null && !mapLst.isEmpty()) {
			for (Map<String, Object> map : mapLst) {
				Object val = map.get(key);
				if (val != null) {
					buffer.append(val).append(",");
				}

			}
		}

		return buffer.length() > 0 ? buffer.substring(0, buffer.length() - 1).toString() : "";
	}

	/**
	 * 从mapLst中获得第一个map(key)的值
	 * 
	 * @param key
	 * @param mapLst
	 * @return
	 */
	public static Object getValByKey(String key, List<Map<String, Object>> mapLst) {
		Object val = null;

		if (mapLst != null && !mapLst.isEmpty()) {
			for (Map<String, Object> map : mapLst) {
				val = map.get(key);
				if (val != null) {
					break;
				}
			}
		}
		return val;
	}

	/**
	 * 从mapLst中获得map(key)的集合
	 * 
	 * @param key
	 * @param mapLst
	 * @return
	 */
	public static Set<String> getValSetByKey(String key, List<Map<String, Object>> mapLst) {
		Set<String> valSet = new HashSet<String>();

		if (mapLst != null && !mapLst.isEmpty()) {
			for (Map<String, Object> map : mapLst) {
				Object val = map.get(key);
				if (val != null) {
					valSet.add(val.toString());
				}
			}
		}
		return valSet;
	}

	/**
	 * 从mapLst中获得map(key)的集合
	 * 
	 * @param key
	 * @param mapLst
	 * @return
	 */
	public static List<String> getValListByKey(String key, List<Map<String, Object>> mapLst) {
		List<String> valSet = new ArrayList<String>();

		if (mapLst != null && !mapLst.isEmpty()) {
			for (Map<String, Object> map : mapLst) {
				Object val = map.get(key);
				if (val != null) {
					valSet.add(val.toString());
				}
			}
		}
		return valSet;
	}

	/**
	 * 以map(key)对mapList进行分组
	 * 
	 * @param key
	 * @param mapList
	 */
	public static Map<String, List<Map<String, Object>>> groupListByCond(String key,
			List<Map<String, Object>> mapList) {
		Map<String, List<Map<String, Object>>> groupMap = new HashMap<String, List<Map<String, Object>>>();
		if (mapList != null && !mapList.isEmpty()) {
			for (Map<String, Object> map : mapList) {
				String valTemp = String.valueOf(map.get(key));
				if (groupMap.containsKey(valTemp)) {
					groupMap.get(valTemp).add(map);
				} else {
					List<Map<String, Object>> subList = new ArrayList<Map<String, Object>>();
					subList.add(map);
					groupMap.put(valTemp, subList);
				}
			}
		}
		return groupMap;
	}

	/**
	 * 
	 * @param key
	 * @param valueKey
	 * @param mapList
	 * @return
	 */
	public static Map<String, String> groupListByCond2(String key, String valueKey, List<Map<String, Object>> mapList) {
		Map<String, String> groupMap = new HashMap<String, String>();
		if (mapList != null && !mapList.isEmpty()) {
			for (Map<String, Object> map : mapList) {
				String valTemp = String.valueOf(map.get(key));
				String valTemp2 = String.valueOf(map.get(valueKey));
				groupMap.put(valTemp, valTemp2);
			}
		}
		return groupMap;
	}

}
