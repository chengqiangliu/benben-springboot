package com.benben.client.response;

import com.benben.global.http.ResponseStatus;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpStatus;

import java.util.Map;

@Setter
@Getter
public class RawApiResponse<T> {

    private Map<String, String> headerMap;

    private T responseBody;

    private ResponseStatus responseStatus;

    private int statusCode;

    public RawApiResponse(Map<String, String> headerMap, T responseBody, ResponseStatus status) {
        this(headerMap, responseBody, status, HttpStatus.SC_OK);
    }

    public RawApiResponse(Map<String, String> headerMap, T responseBody, ResponseStatus responseStatus, int statusCode) {
        this.headerMap = headerMap;
        this.responseBody = responseBody;
        this.responseStatus = responseStatus;
        this.statusCode = statusCode;
    }
}
