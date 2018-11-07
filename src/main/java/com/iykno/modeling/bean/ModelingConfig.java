package com.iykno.modeling.bean;

import java.util.List;

/**
 * 建模配置
 * 
 * @author iykno
 *
 */
public class ModelingConfig {

	private String topic;
	private String mainColumn;//
	private String mainTable;// 主表
	private Integer period;// 执行周期
	private List<TableModel> tables;// 从表

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getMainColumn() {
		return mainColumn;
	}

	public void setMainColumn(String mainColumn) {
		this.mainColumn = mainColumn;
	}

	public String getMainTable() {
		return mainTable;
	}

	public void setMainTable(String mainTable) {
		this.mainTable = mainTable;
	}

	public List<TableModel> getTables() {
		return tables;
	}

	public void setTables(List<TableModel> tables) {
		this.tables = tables;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

}
