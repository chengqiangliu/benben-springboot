package com.benben.auth.exception;

import com.benben.auth.error.BenbenAuthError;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class AuthValidationException extends RuntimeException {

    private String errorCode;

    private String errorMessage;

    public AuthValidationException(BenbenAuthError authError) {
        super();
        this.errorCode = authError.getCode();
        this.errorMessage = authError.getMessage();
    }

    public AuthValidationException(BenbenAuthError authError, Exception ex) {
        super();
        this.errorCode = authError.getCode();
        this.errorMessage = authError.getMessage() + ex.getMessage();
    }
}
