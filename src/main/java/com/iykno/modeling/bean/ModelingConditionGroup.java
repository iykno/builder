package com.iykno.modeling.bean;

import java.util.List;

/**
 * 
 * 建模查询 条件组
 * 
 * @author iykno
 *
 */
public class ModelingConditionGroup {

	private int pageNo = 1;

	private int pageSize = 10;

	private String topic;// 主题必传 对应如 device

	private List<ModelingCondition> conditionList;// 过滤条件

	private List<ModelingConditionOrderBy> orderByList;// 排序条件

	public List<ModelingCondition> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<ModelingCondition> conditionList) {
		this.conditionList = conditionList;
	}

	public List<ModelingConditionOrderBy> getOrderByList() {
		return orderByList;
	}

	public void setOrderByList(List<ModelingConditionOrderBy> orderByList) {
		this.orderByList = orderByList;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
