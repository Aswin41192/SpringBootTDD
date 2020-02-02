package com.spring.tdd.utils;

import com.spring.tdd.model.ResponseVO;

public class ResponseUtils {

	public static ResponseVO handleSuccessfulResponse(ResponseVO responseVO,Object response,String message) {
		responseVO.setSuccess(true);
		responseVO.setResponse(response);
		responseVO.setMessage(message);
		return responseVO;
	}
	
	public static ResponseVO handleFailureResponse(ResponseVO responseVO,String message) {
		responseVO.setSuccess(false);
		responseVO.setMessage(message);
		responseVO.setResponse(null);
		return responseVO;
	}
	
	public static void throwIllegalArgumentException(String message) {
		throw new IllegalArgumentException(message);
	}
}
