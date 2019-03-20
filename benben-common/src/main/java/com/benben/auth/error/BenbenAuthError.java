package com.benben.auth.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BenbenAuthError {

    REQUIRED_AUTH_NOT_FOUND("AU0001", "authentication header is not found"),

    INVALID_SIGNATURE("AU0002", "signature is invalid"),

    INVALID_NUMBER_OF_AUTH_FIELD("AU0003", "The number of auth field is invalid"),

    INVALID_GROUP_NO("AU0004", "The groupNo is invalid"),

    TIMESTAMP_EXPIRED("AU0005", "The signature is expired"),

    SIGNATURE_NOT_MATCH("AU0006", "The signature does not match"),

    ;

    /** code */
    private String code;

    /** message */
    private String message;
}
