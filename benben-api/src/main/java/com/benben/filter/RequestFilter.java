package com.benben.filter;

import com.benben.global.constants.ContextField;
import com.benben.global.http.BodyReadHttpServletRequest;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest rawRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.clear();
        MDC.put(ContextField.REQUEST_ID, UUID.randomUUID().toString());
        if (rawRequest instanceof HttpServletRequest) {
            BodyReadHttpServletRequest bodyReadRequest = new BodyReadHttpServletRequest(rawRequest);
            chain.doFilter(bodyReadRequest, response);
        } else {
            chain.doFilter(rawRequest, response);
        }
    }

    @Override
    public void destroy() {

    }
}
