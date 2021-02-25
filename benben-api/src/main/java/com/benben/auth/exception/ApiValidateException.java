package com.benben.auth.exception;

import com.benben.auth.error.ApiError;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class ApiValidateException extends RuntimeException {

    private String errorCode;

    private String errorMessage;

    public ApiValidateException (ApiError apiError) {
        super();
        this.errorCode = apiError.getCode();
        this.errorMessage = apiError.getMessage();
    }

    public ApiValidateException (ApiError apiError, Exception ex) {
        super();
        this.errorCode = apiError.getCode();
        this.errorMessage = apiError.getMessage() + ex.getMessage();
    }
}
