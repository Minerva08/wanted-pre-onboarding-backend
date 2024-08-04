package com.example.preboarding.exception;

import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorHandlerDetailObject {
	@Schema(description = "파라미터 이름" , example = "userId")
	private String param;
	@Schema(description = "입력 값" , example = "moon")
	private String value;
	
	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
