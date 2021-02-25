package com.benben.exception;

import com.benben.error.BenbenBatchError;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class BatchValidationException extends RuntimeException {

    private String errorCode;

    private String errorMessage;

    public BatchValidationException(BenbenBatchError batchError) {
        super();
        this.errorCode = batchError.getCode();
        this.errorMessage = batchError.getMessage();
    }

    public BatchValidationException(BenbenBatchError batchError, Exception ex) {
        super();
        this.errorCode = batchError.getCode();
        this.errorMessage = batchError.getMessage() + ex.getMessage();
    }
}
