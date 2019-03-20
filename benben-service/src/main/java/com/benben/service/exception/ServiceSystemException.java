package com.benben.service.exception;

import com.benben.service.error.BenbenServiceError;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class ServiceSystemException extends RuntimeException {

    private String errorCode;

    private String errorMessage;

    public ServiceSystemException(BenbenServiceError serviceError) {
        super();
        this.errorCode = serviceError.getCode();
        this.errorMessage = serviceError.getMessage();
    }

    public ServiceSystemException(BenbenServiceError serviceError, Exception ex) {
        super();
        this.errorCode = serviceError.getCode();
        this.errorMessage = serviceError.getMessage() + ex.getMessage();
    }
}
