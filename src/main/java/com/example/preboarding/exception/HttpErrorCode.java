package com.example.preboarding.exception;


public enum HttpErrorCode {
	SUCCESS(200, "COMMON-OK-200", "SUCCESS"),
	BAD_REQUEST(400, "COMMON-ERR-400", "BAD REQUEST"),
	UNAUTHORIZED(401, "COMMON-ERR-401", "UNAUTHORIZED"),
	FORBIDDEN(403, "COMMON-ERR-403", "FORBIDDEN : NEED ACCESS RIGHT"),
	NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
	CONFLICT(409, "COMMON-ERR-409", "CANNOT PROCEED. NEED PRE-PROCESSING"),
    INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTERNAL SERVER ERROR"),
    ;

	private int status;
	private String errorCode;
	private String errorMessage;
	
	HttpErrorCode(int status, String errorCode, String errorMessage) {
		// TODO Auto-generated constructor stub
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	
	public int getStatus() {
		return status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static HttpErrorCode findByStatus (int status) {
		HttpErrorCode[] httpErrors = HttpErrorCode.values();
		HttpErrorCode retError = null;
		for (int i = 0; i < httpErrors.length ; i++) {
			if (httpErrors[i].status == status) {
				retError = httpErrors[i];
				break;
			}
		}
		return retError;
	}
	
}
