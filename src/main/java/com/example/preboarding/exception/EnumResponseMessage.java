package com.example.preboarding.exception;

import lombok.Getter;

@Getter
public enum EnumResponseMessage {
	MESSAGE_NO_USER       		(1000, "NO-USER",  "등록되지 않은 사용자 입니다"),
	MESSAGE_ER_COMPANY    		(2000, "ER-C-ROLE", "해당 회사가 존재하지 않습니다"),
	MESSAGE_NO_JOB_POSITION     (3000, "NO-J-POSITION",  "해당 채용 공고가 없습니다."),
	MESSAGE_ER_AL_JOB_POSITION     (3001, "ER-AL-J-POSITION",  "해당 직무의 채용 공고가 이미 등록 되어있습니다"),
	MESSAGE_ER_APPLY    		(4000, "ER-APPLY",  "해당 공고에 지원할 수 없습니다"),
	MESSAGE_NO_APPLY    		(4001, "NO-APPLY",  "지원 내역이 없습니다"),
	MESSAGE_ER_C_ROLE    		(5000, "ER-C-ROLE", "해당 회사에 등록 되지 않은 직무 입니다"),
	MESSAGE_ER_ROLE    			(6000, "ER-C-ROLE", "해당 역할을 등록할 수 없습니다"),
	MESSAGE_NO_REQUIRED    			(7000, "NO-REQUIRE", "필수 요청값이 없습니다"),
	;
	
	private int code;
	private String errorCode;
	private String errorMessage;
	
	EnumResponseMessage(int code, String errorCode, String errorMessage) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
