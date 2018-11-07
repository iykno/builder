package com.iykno.modeling.bean;

import java.util.List;

/**
 * 
 * @author iykno
 *
 */
public class TableModel {

	private Integer period;
	private String jsonKey;;
	private String slaveTable;
	private String mappingColumn;
	private int initType;
	private SQLModel sql;

	private List<TableModel> tables;// 从表

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getJsonKey() {
		return jsonKey;
	}

	public void setJsonKey(String jsonKey) {
		this.jsonKey = jsonKey;
	}

	public String getSlaveTable() {
		return slaveTable;
	}

	public void setSlaveTable(String slaveTable) {
		this.slaveTable = slaveTable;
	}

	public String getMappingColumn() {
		return mappingColumn;
	}

	public void setMappingColumn(String mappingColumn) {
		this.mappingColumn = mappingColumn;
	}

	public int getInitType() {
		return initType;
	}

	public void setInitType(int initType) {
		this.initType = initType;
	}

	public SQLModel getSql() {
		return sql;
	}

	public void setSql(SQLModel sql) {
		this.sql = sql;
	}

	public List<TableModel> getTables() {
		return tables;
	}

	public void setTables(List<TableModel> tables) {
		this.tables = tables;
	}

}
