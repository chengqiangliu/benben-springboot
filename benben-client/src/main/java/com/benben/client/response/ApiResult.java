package com.benben.client.response;

import com.benben.global.http.ResponseStatus;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ApiResult<T> {

    /** API response status */
    private ResponseStatus status;

    /** API response HTTP status */
    private int httpStatus;

    /** API response errors */
    private List<Map<String, String>> errors;

    /** API response content */
    private T content;
}
