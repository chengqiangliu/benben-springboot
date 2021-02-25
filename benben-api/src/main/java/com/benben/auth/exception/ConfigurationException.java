package com.benben.auth.exception;

import com.benben.auth.error.ApiError;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConfigurationException extends RuntimeException {

    private String errorCode;

    private String errorMessage;

    public ConfigurationException (ApiError apiError) {
        super();
        this.errorCode = apiError.getCode();
        this.errorMessage = apiError.getMessage();
    }
}
