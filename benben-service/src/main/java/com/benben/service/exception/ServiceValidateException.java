package com.benben.service.exception;

import com.benben.service.error.BenbenServiceError;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class ServiceValidateException extends RuntimeException {

    private String errorCode;

    private String errorMessage;

    public ServiceValidateException(BenbenServiceError serviceError) {
        super();
        this.errorCode = serviceError.getCode();
        this.errorMessage = serviceError.getMessage();
    }

    public ServiceValidateException(BenbenServiceError serviceError, Exception ex) {
        super();
        this.errorCode = serviceError.getCode();
        this.errorMessage = serviceError.getMessage() + ex.getMessage();
    }
}
