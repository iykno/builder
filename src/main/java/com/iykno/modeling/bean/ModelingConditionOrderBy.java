package com.iykno.modeling.bean;

import com.iykno.modeling.common.CommonConstants;

/**
 * 
 * @author iykno
 *
 */
public class ModelingConditionOrderBy {

	private String block;// 块

	private String attr;// 属性

	private String orderBy;// attr

	private String desc = "ASC";// ASC DESC

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
		if (orderBy != null) {
			String[] arr = orderBy.split(CommonConstants.operator_comma);
			if (arr.length == 2) {
				this.block = arr[0];
				this.attr = arr[1];
			}
		}
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getBlock() {
		if (block == null && orderBy != null) {
			String[] arr = orderBy.split(CommonConstants.operator_comma);
			if (arr.length == 2) {
				this.block = arr[0];
				this.attr = arr[1];
			}
		}

		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getAttr() {
		if (attr == null && orderBy != null) {
			String[] arr = orderBy.split(CommonConstants.operator_comma);
			if (arr.length == 2) {
				this.block = arr[0];
				this.attr = arr[1];
			}
		}
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

}
