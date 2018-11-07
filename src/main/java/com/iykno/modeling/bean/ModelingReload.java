package com.iykno.modeling.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据重载
 * 
 * @author iykno
 *
 */
public class ModelingReload {

	private String topic;//

	/**
	 * mainColumn主字段维度重载
	 */
	private List<String> mainColumnVals;

	/**
	 * 通过jsonKey维度去重载刷新
	 */
	private List<String> jsonKeys;

	public ModelingReload() {
		super();
	}

	public ModelingReload(String topic, String jsonKey) {
		super();
		this.topic = topic;
		this.jsonKeys = new ArrayList<>();
		getJsonKeys().add(jsonKey);
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public List<String> getMainColumnVals() {
		return mainColumnVals;
	}

	public void setMainColumnVals(List<String> mainColumnVals) {
		this.mainColumnVals = mainColumnVals;
	}

	public List<String> getJsonKeys() {
		return jsonKeys;
	}

	public void setJsonKeys(List<String> jsonKeys) {
		this.jsonKeys = jsonKeys;
	}

	@Override
	public String toString() {
		return "ModelingReload [topic=" + topic + ", mainColumnVals=" + mainColumnVals + ", jsonKeys=" + jsonKeys + "]";
	}

}
