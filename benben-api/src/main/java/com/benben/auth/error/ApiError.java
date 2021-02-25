package com.benben.auth.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiError {

    HTTP_MEDIA_TYPE_NOT_SUPPORTED("EA9001", "HttpMediaType is not supported."),

    HTTP_METHOD_ERROR("EA9002", "Request method type is not supported."),

    ENDPOINT_DOES_NOT_EXIST("EA9003", "The request endpoint is not found."),

    SYSTEM_ERROR("EA9998", "System error occur"),

    UNKNOWN_ERROR("EA9999", "Unknown error occur"),

    EXTERNAL_CODE_IS_REQUIRED("EA8001", "externalCode field is required in the error-mapping yaml file"),

    EXTERNAL_CODE_DOES_NOT_EXIST("EA8002", "External error code is not found in the external-error yaml file"),

    MESSAGE_IS_REQUIRED("EA8003", "message field is required in the external-error yaml file"),

    HTTP_STATUS_IS_REQUIRED("EA8004", "httpStatus field is required in the external-error yaml file"),

    REPLACEMENT_DOES_NOT_EXIST("EA8005", "replacement is not found in the external-error yaml file"),

    DEFAULT_ERROR_MAPPING_DOES_NOT_EXIST("EA8006", "Default error mapping is not found in the error-mapping yaml file"),

    READING_CONFIGURATION_FILE_ERROR("EA8007", "Error occur when reading configuration file"),



    INVALID_USERNAME("WA0001", "The value of username is invalid");

    /** code */
    private String code;

    /** message */
    private String message;
}
