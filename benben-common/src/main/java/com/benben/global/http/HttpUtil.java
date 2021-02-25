package com.benben.global.http;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {

    public static String getFullURL(HttpServletRequest request) {

        StringBuffer requestURL;
        String forwardedProto = request.getHeader("x_forwarded_proto");
        if (forwardedProto != null && forwardedProto != "") {
            String originBaseUrl = forwardedProto + "://" + request.getHeader("x_forwarded_host");
            requestURL = new StringBuffer(originBaseUrl).append(request.getRequestURI());
        } else {
            requestURL = request.getRequestURL();
        }

        String queryString = request.getQueryString();
        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
