package com.example.preboarding.exception;

import java.util.ArrayList;
import java.util.List;

public class CustomException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private HttpErrorCode errorCode;
    private EnumResponseMessage enumErrorMessage;
    private List<ErrorHandlerDetailObject> detail;

    public CustomException(HttpErrorCode errorCode, EnumResponseMessage enumResponse) {
        super(enumResponse.getErrorMessage());
        this.errorCode = errorCode;
        this.enumErrorMessage = enumResponse;
    }

    public void addErrorDetail(String param, String value ) {
        if (this.detail == null) detail = new ArrayList<ErrorHandlerDetailObject>();
        ErrorHandlerDetailObject map = new ErrorHandlerDetailObject();
        map.setParam(param);
        map.setValue(value);
        this.detail.add(map);
    }
}
