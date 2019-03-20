package com.benben.client.exception;

import com.benben.client.error.CallApiError;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Setter
@Getter
public class ApiCallException extends RuntimeException {

    private String errorCode;

    private String errorMessage;

    public ApiCallException(CallApiError callApiError) {
        super();
        this.errorCode = callApiError.getCode();
        this.errorMessage = callApiError.getMessage();
    }

    public ApiCallException(CallApiError callApiError, IOException ex) {
        super();
        this.errorCode = callApiError.getCode();
        this.errorMessage = callApiError.getMessage() + ex.getMessage();
    }
}
