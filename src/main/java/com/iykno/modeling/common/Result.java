package com.iykno.modeling.common;

import com.alibaba.fastjson.JSONObject;

public class Result extends JSONObject {

	private static final long serialVersionUID = -3704030906158134520L;

	public Result(String resultCode) {
	}

	public Result(Integer pageNo, Integer rows, Integer pageSize) {
		this.put("startNo", pageNo);
		this.put("count", rows);
		this.put("pageSize", pageSize);
	}

	public Result(Integer pageSize) {
		this.put("TotalNum", pageSize);
	}

	public Result(Integer pageSize, boolean flag) {
		this.put("TotalNum", pageSize);
		this.put("nextPage", flag);
	}

	public void setResultCode(String resultCode) {
		this.put("resultCode", resultCode);
	}

	public String getResultCode() {
		return (String) this.get("resultCode");
	}

	public void setResultMessage(String resultMessage) {
		this.put("resultMessage", resultMessage);
	}

	public void setData(Object data) {
		if (data == null) {
			this.put("data", "");
		} else {
			this.put("data", data);
		}

	}

	public Object getData() {
		return this.get("data");
	}

	public void setTotalNum(long totalCount) {
		this.put("TotalNum", totalCount);
	}

}
