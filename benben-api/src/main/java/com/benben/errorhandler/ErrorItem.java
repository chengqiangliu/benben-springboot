package com.benben.errorhandler;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ErrorItem {

    /** code */
    private String code;

    /** message */
    private String message;

    public ErrorItem(ExternalError externalError) {
        this.code = externalError.getErrorCode();
        this.message = externalError.getErrorMessage();
    }
}
