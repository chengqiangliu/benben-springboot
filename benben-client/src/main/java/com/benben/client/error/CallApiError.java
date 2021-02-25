package com.benben.client.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CallApiError {

    UNDEFINED_ERROR("EC9999", "Undefined error"),

    HTTP_CLIENT_CAN_NOT_CLOSE("EC9001", "Http client can not close"),

    HTTP_PROTOCOL_ERROR("EC9002", "Http protocol error"),

    ;

    /** code */
    private String code;

    /** message */
    private String message;
}
