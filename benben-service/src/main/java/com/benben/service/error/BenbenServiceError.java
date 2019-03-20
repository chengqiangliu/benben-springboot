package com.benben.service.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BenbenServiceError {

    UNKNOWN_ERROR("ES9999", "Unknown error occur"),

    SYSTEM_ERROR("ES9998", "System error occur"),

    GROUP_IS_NOT_FOUND("WS0001", "The group is not found"),

    GROUP_ALREADY_EXISTS("WS0002", "The group is already exists"),

    NO_GROUP_IS_FOUND("WS0003", "There is no group exists"),

    GROUP_CAN_NOT_BE_DELETED_WITH_USERS("WS0004", "The group which contains users in it can not be deleted"),

    USER_IS_NOT_FOUND("WS0005", "The user is not found"),

    OLD_PASSWORD_IS_NOT_CORRECT("WS0006", "The old password is not correct"),

    THE_USER_WAS_UPDATED_BY_OTHER("WS0007", "The user was updated by others"),

    USERNAME_IS_DUPLICATED("WS0008", "The username is duplicated"),

    BANKCARD_ALREADY_EXISTS("WS0009", "The bankcard is already exists"),

    BANKCARD_IS_NOT_FOUND("WS0010", "The bankcard is not found"),

    CASH_ALREADY_EXISTS("WS0011", "The cash is already exists"),

    CASH_IS_NOT_FOUND("WS0012", "The cash is not found"),

    ;

    /** code */
    private String code;

    /** message */
    private String message;
}
