package com.iykno.modeling.comparator;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import com.alibaba.druid.util.StringUtils;
import com.iykno.modeling.bean.ModelingConditionOrderBy;
import com.iykno.modeling.common.CollectionUtils;
import com.iykno.modeling.common.CommonConstants;

/**
 * 建模比较器
 * 
 * @author iykno
 *
 */
public class ModelingComparator implements Comparator<Map<String, List<Map<String, Object>>>> {

	private List<ModelingConditionOrderBy> orderByList;

	public ModelingComparator(List<ModelingConditionOrderBy> orderByList) {
		this.orderByList = orderByList;
	}

	@Override
	public int compare(Map<String, List<Map<String, Object>>> o1, Map<String, List<Map<String, Object>>> o2) {
		if (orderByList == null || orderByList.isEmpty()) {
			return 0;
		}

		int result = 0;
		for (ModelingConditionOrderBy orderBy : orderByList) {
			int result0 = compare0(orderBy, o1, o2);
			// 比较得到不相同
			if (result0 != 0) {
				result = result0;
				break;
			}
		}

		return result;
	}

	private int compare0(ModelingConditionOrderBy orderBy, Map<String, List<Map<String, Object>>> o1,
			Map<String, List<Map<String, Object>>> o2) {

		if (!StringUtils.isEmpty(orderBy.getOrderBy())) {
			String[] arr = orderBy.getOrderBy().split(CommonConstants.operator_point);
			if (arr.length == 2) {

				// TODO 只取了list的第一条数据出来 去做排序
				Object val1 = CollectionUtils.getValByKey(arr[1], o1.get(arr[0]));
				Object val2 = CollectionUtils.getValByKey(arr[1], o2.get(arr[0]));

				// 必须保证传递性 ,以下这段对null值的判断很有必要
				// 皆为null 认为相等
				if (val1 == null && val2 == null) {
					return 0;
				}
				// 谁不为空 谁大
				else if (val1 != null && val2 == null) {
					return 1;
				}
				// 谁不为空 谁大
				else if (val1 == null && val2 != null) {
					return -1;
				} else {
					return "ASC".equalsIgnoreCase(orderBy.getDesc()) ? compare0(val1, val2)
							: (-1 * compare0(val1, val2));
				}

			}
		}

		return 0;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int compare0(Object o1, Object o2) {
		int ret = 0;

		// 字符串类型单独处理，判断是否是数字，如果是数字，转数字进行比较
		if (o1 instanceof String && o2 instanceof String) {
			String s1 = (String) o1;
			String s2 = (String) o2;
			boolean s1IsNumber = NumberUtils.isNumber(s1);
			boolean s2IsNumber = NumberUtils.isNumber(s2);

			// 如果都为数字，照数字比较
			if (s1IsNumber && s2IsNumber) {
				if (s1.length() < 10) {
					ret = Double.compare(Double.valueOf(s1), Double.valueOf(s2));// 数字太长会会异常，如 导致32032478901234500421
																					// 32032478901234500422 相等
				} else {
					BigDecimal bigDecimal1 = NumberUtils.createBigDecimal(s1);
					BigDecimal bigDecimal2 = NumberUtils.createBigDecimal(s2);
					ret = bigDecimal1.compareTo(bigDecimal2);
				}

			} else {
				if (s1IsNumber && !s2IsNumber) {
					ret = 1;
				} else if (!s1IsNumber && s2IsNumber) {
					ret = -1;
				} else {
					ret = s1.compareTo(s2);
				}

			}
		} else if (o1 instanceof Comparable && o2 instanceof Comparable) {
			Comparable c1 = (Comparable) o1;
			Comparable c2 = (Comparable) o2;
			ret = c1.compareTo(c2);
		} else {
			// 不可比类型的，均相等
			ret = 0;
		}
		return ret;
	}

}
