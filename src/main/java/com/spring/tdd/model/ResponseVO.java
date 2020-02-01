package com.spring.tdd.model;

import org.springframework.stereotype.Component;

@Component
public class ResponseVO {

	private boolean success;
	private String message;
	private Object response;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	
	@Override
	public String toString() {
		return "ResponseVO [success=" + success + ", message=" + message + ", response=" + response + "]";
	}
	
}
