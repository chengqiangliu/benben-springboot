package com.benben.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ExternalError {

    /** errorCode */
    private String errorCode;

    /** message */
    private String errorMessage;

    /** httpStatus */
    private Integer httpStatus;
}
