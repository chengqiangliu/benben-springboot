package com.benben.dao.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BenbenDaoError {

    UNKNOWN_ERROR("ED9999", "Unknown error occur"),

    SYSTEM_ERROR("ED9998", "System error occur"),

    DB_IS_DISCONNECT("ED0001", "The database connection has been cut");

    /** code */
    private String code;

    /** message */
    private String message;
}
