package com.haobai.base.utils;

public class HttpResult {

	// 响应码
	private Integer returnCode;

	// 响应体
	private String msg;

	public HttpResult() {
		super();
	}

	public HttpResult(Integer returnCode, String msg) {
		super();
		this.returnCode = returnCode;
		this.msg = msg;
	}

	public Integer getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


}
