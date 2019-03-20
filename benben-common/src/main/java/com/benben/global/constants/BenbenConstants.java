package com.benben.global.constants;

import java.nio.charset.Charset;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
public interface BenbenConstants {

    String ASYNC_EXECUTOR_BEAN_NAME = "async_executor_bean";

    Charset UTF8_CHARSET = Charset.forName("UTF-8");

    String PRODUCE_CHARSET = ";charset=utf-8";

    String SEPARATOR_SEMICOLON = ";";

    String SEPARATOR_HYPHEN = "-";

    String SEPARATOR_EQUAL = "=";

    String SEPARATOR_AND = "&";

    String SEPARATOR_QUESTION = "?";

    String BLANK_STRING = "";

    String HEADER_AUTHORIZATION = "Authorization";

    String PROTOCAL_HTTPS = "https";

    String CONTENT_ENCODING = "UTF-8";

    String JSON_CONTENT_TYPE = "application/json";

    int HTTP_MAX_TOTAL = 100;

    int HTTP_MAX_PER_ROUTE = 100;

    int HTTP_SOCKET_TIMEOUT = 500000;

    int HTTP_CONNECT_TIMEOUT = 500000;
}
