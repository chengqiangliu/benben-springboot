package com.benben.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BenbenBatchError {

    UNKNOWN_ERROR("EB9999", "Unknown error occur"),

    MISSING_COMMAND("EB0001","Validation Exception: Missing args. {command} is mandatory. Usage: java -jar batch.jar -command {command}."),

    INVALID_COMMAND("EB0002", "Validation Exception: Invalid command."),

    ;

    /** code */
    private String code;

    /** message */
    private String message;
}
