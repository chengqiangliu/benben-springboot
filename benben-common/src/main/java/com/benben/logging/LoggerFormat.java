package com.benben.logging;

public class LoggerFormat {

    /** COMMON_ERROR (errorCode, message) */
    public static final String COMMON_ERROR = "err|{}|errMsg={}";

    /** COMMON_ERROR_WITH_PARAM (errorCode, message, extraInfo) */
    public static final String COMMON_ERROR_WITH_PARAM = "err|{}|errMsg={}|{}";

    /** COMMON_EXCEPTION (errorCode, message, cause, stackTrace) */
    public static final String COMMON_EXCEPTION = "err|{}|errMsg={}|cause={}|stack={}";

    /** COMMON_EXCEPTION (errorCode, message, param, cause, stackTrace) */
    public static final String COMMON_EXCEPTION_WITH_PARAM = "err|{}|errMsg={}|{}|stack|cause={}|{}";

    /** ERROR_REQUEST (httpStatus, requestPath, extraInfo) */
    public static final String ERROR_REQUEST = "errorRequest|httpStatus={}|requestPath={}|{}";

    /** REQUEST_PARAM (method, url, body, header) */
    public static final String REQUEST_PARAM = "req|{}|{}|{}|{}";

    /* dao error log format */
    public static final String DAO_EXCEPTION = "error|{}|{}|{}";

    /* performance log format */
    public static final String PERFORMANCE = "performance|{}|{}|{}";

    /* response log format */
    public static final String RESPONSE = "response|{}";
}
