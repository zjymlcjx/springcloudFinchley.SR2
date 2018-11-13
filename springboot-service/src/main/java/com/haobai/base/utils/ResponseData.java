package com.haobai.base.utils;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseData<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T data;
	private String result;
	private List<MessageInfo> messageInfo;
	private String message;

	public ResponseData() {

	}

	public ResponseData(T data, String result, List<MessageInfo> messageInfo) {
		this.data = data;
		this.result = result;
		this.messageInfo = messageInfo;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<MessageInfo> getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(List<MessageInfo> messageInfo) {
		this.messageInfo = messageInfo;
	}

	public String toString() {
		return String.format("values:", result, messageInfo.toString(), data.toString());
	}

}
