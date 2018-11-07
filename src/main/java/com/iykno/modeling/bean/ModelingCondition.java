package com.iykno.modeling.bean;

/**
 * 
 * 建模查询条件
 * 
 * @author iykno
 *
 */
public class ModelingCondition {

	private String condition;// 格式如block.attr

	private String conditionVal;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;

	}

	public String getConditionVal() {
		return conditionVal;
	}

	public void setConditionVal(String conditionVal) {
		this.conditionVal = conditionVal;
	}
}
