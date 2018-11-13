package com.haobai.base.utils;

import java.util.List;

public class MessageInfo {

	private String messageCode;
	private List<String> params;

	public MessageInfo() {

	}

	public MessageInfo(String messageCode, List<String> params) {
		this.messageCode = messageCode;
		this.params = params;
	}

	public MessageInfo(String messageCode) {
		this.messageCode = messageCode;
		this.params = null;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

}
