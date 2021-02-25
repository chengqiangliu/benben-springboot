package com.benben.client;

import com.benben.client.exception.ApiCallException;
import com.benben.client.error.CallApiError;
import com.benben.client.response.ApiResult;
import com.benben.client.response.RawApiResponse;
import com.benben.global.constants.BenbenConstants;
import com.benben.global.http.ResponseStatus;
import com.benben.logging.Loggers;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.javafx.jmx.json.JSONException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseClient {

    private static ObjectMapper objectMapper = new ObjectMapper();

    // Create a custom response handler
    final ResponseHandler<RawApiResponse<String>> responseHandler = new ResponseHandler<RawApiResponse<String>>() {
        @Override
        public RawApiResponse<String> handleResponse(final HttpResponse response)
                throws IOException {

            final Map<String, String> headerMap = new HashMap<>();
            // parse out the headers
            for (final Header h : response.getAllHeaders()) {
                headerMap.put(h.getName(), h.getValue());
            }

            // get response body in string
            final HttpEntity entity = response.getEntity();
            final String bodyString = entity != null ? EntityUtils.toString(entity) : null;

            // get response status code
            final int statusCode = response.getStatusLine().getStatusCode();
            ResponseStatus responseStatus = (statusCode == HttpStatus.SC_OK)
                    ? ResponseStatus.OK
                    : ResponseStatus.ERROR;

            return new RawApiResponse<>(headerMap, bodyString, responseStatus, statusCode);
        }
    };

    public RawApiResponse<String> makeHttpsGetRequest(final CloseableHttpClient httpClient, final String apiUrl,
                                                      final String authHeader, final Map<String, Object> params) throws ApiCallException {

        try {
            String requestUrl = apiUrl;
            final StringBuffer param = new StringBuffer();
            int i = 0;
            if (params != null && !params.isEmpty()) {
                for (final String key : params.keySet()) {
                    if (i == 0) {
                        param.append(BenbenConstants.SEPARATOR_QUESTION);
                    } else {
                        param.append(BenbenConstants.SEPARATOR_AND);
                    }
                    param.append(key).append(BenbenConstants.SEPARATOR_EQUAL).append(params.get(key));
                    i++;
                }
            }
            requestUrl += param;

            final HttpGet httpGet = new HttpGet(requestUrl);
            if (authHeader != null) {
                httpGet.setHeader(BenbenConstants.HEADER_AUTHORIZATION, authHeader);
            }

            final RawApiResponse<String> response = httpClient.execute(httpGet, this.responseHandler);
            return response;
        } catch (final ClientProtocolException e) {
            throw new ApiCallException(CallApiError.HTTP_PROTOCOL_ERROR, e);
        } catch (final IOException e) {
            throw new ApiCallException(CallApiError.UNDEFINED_ERROR, e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (final IOException t) {
                throw new ApiCallException(CallApiError.HTTP_CLIENT_CAN_NOT_CLOSE, t);
            }
        }
    }

    public RawApiResponse<String> makeHttpsPostRequest(final CloseableHttpClient httpClient, final String apiUrl,
                                                       final String authHeader, final String jsonStr) throws ApiCallException {

        try {
            final StringEntity stringEntity = new StringEntity(jsonStr, StandardCharsets.UTF_8);
            stringEntity.setContentEncoding(BenbenConstants.CONTENT_ENCODING);
            stringEntity.setContentType(BenbenConstants.JSON_CONTENT_TYPE);

            final HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setEntity(stringEntity);
            httpPost.setHeader(BenbenConstants.HEADER_AUTHORIZATION, authHeader);
            return httpClient.execute(httpPost, this.responseHandler);
        } catch (final IOException e) {
            throw new ApiCallException(CallApiError.UNDEFINED_ERROR, e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (final IOException t) {
                throw new ApiCallException(CallApiError.HTTP_CLIENT_CAN_NOT_CLOSE, t);
            }
        }
    }

    protected <T> ApiResult<T> getApiResultFromRawResponse(RawApiResponse<String> response, Class<T> tClass)
            throws ApiCallException {
        ApiResult<T> apiResult = new ApiResult<>();
        String bodyString = response.getResponseBody();
        ResponseStatus status = response.getResponseStatus();
        // OK or ERROR
        apiResult.setStatus(status);
        // Http status
        apiResult.setHttpStatus(response.getStatusCode());
        if (ResponseStatus.OK == status && tClass != null) {
            try {
                if (StringUtils.isNotEmpty(bodyString)) {
                    T responseObj = this.objectMapper.readValue(bodyString, tClass);
                    apiResult.setContent(responseObj);
                }
            } catch (JsonParseException e) {
                apiResult.setHttpStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                Loggers.API_CLIENT.warn("Cannot parse to JSON bodyString={} err={}", bodyString, e.toString());
            } catch (IOException e) {
                apiResult.setHttpStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                Loggers.API_CLIENT.warn("Network discrepancy bodyString={} err={}", bodyString, e.toString());
            }
        } else {
            try {
                List<Map<String, String>> errorList = this.generateErrorList(bodyString);
                apiResult.setErrors(errorList);
            } catch (JSONException e) {
                apiResult.setHttpStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
                Loggers.API_CLIENT.warn("Cannot parse string to JSON bodyString={} err={}", bodyString, e.toString());
            }
        }
        return apiResult;
    }

    private Map<String, String> convertJsonStringToMap(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> jsonMap;
        try {
            jsonMap = mapper.readValue(jsonString, new TypeReference<Map<String, String>>() {});
        } catch (IOException e) {
            return null;
        }

        return jsonMap;
    }

    private List<Map<String, String>> generateErrorList(String jsonString) {
        JSONArray jsonArray = new JSONArray(jsonString);
        List<Map<String, String>> errorList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            errorList.add(this.convertJsonStringToMap(jsonArray.getJSONObject(i).toString()));
        }
        return errorList;
    }

    public ResponseHandler<RawApiResponse<String>> getResponseHandler() {
        return this.responseHandler;
    }
}
