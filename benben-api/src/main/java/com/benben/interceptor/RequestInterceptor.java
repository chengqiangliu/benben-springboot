package com.benben.interceptor;

import com.benben.annotation.BenbenApiEndpoint;
import com.benben.auth.AuthHeader;
import com.benben.auth.RequestAuthenticator;
import com.benben.global.constants.ApiName;
import com.benben.global.constants.ContextField;
import com.benben.global.http.HttpUtil;
import com.benben.global.http.BodyReadHttpServletRequest;
import com.benben.logging.LoggerFormat;
import com.benben.logging.Loggers;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private RequestAuthenticator requestAuthenticator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // Because 1st time preHandle#handler is “AbstractHandlerMapping$PreFlightHandler” when CrossOrigin
        if (!(handler instanceof HandlerMethod)) {
            Loggers.HTTP_LOGGER.debug("Non-handlerMethod caught: {}", handler.getClass());
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        BenbenApiEndpoint apiAnnotation = handlerMethod.getMethod().getAnnotation(BenbenApiEndpoint.class);
        if (apiAnnotation != null) {
            BodyReadHttpServletRequest bodyReadRequest = (BodyReadHttpServletRequest) request;
            String requestUrl =  HttpUtil.getFullURL(bodyReadRequest);
            MDC.put(ContextField.HTTP_REQUEST_URL, requestUrl);

            String requestBody = bodyReadRequest.getStringBody();
            Loggers.HTTP_LOGGER.info(LoggerFormat.REQUEST_PARAM,
                    request.getMethod(),
                    requestUrl,
                    requestBody,
                    request.getHeader(HttpHeaders.AUTHORIZATION));

            ApiName apiName = apiAnnotation.apiName();
            MDC.put(ContextField.API_NAME, apiName.getName());
            if (!apiAnnotation.authentication()) {
                return true;
            }

            AuthHeader authHeader = new AuthHeader(request.getHeader(HttpHeaders.AUTHORIZATION));

            // TODO: get secretKey by the given groupNo
            String secretKey = "secret";
            this.requestAuthenticator.authenticateRequest(authHeader, requestUrl, requestBody, secretKey);

            // TODO: authentication check
            return true;
        } else {
            Loggers.HTTP_LOGGER.warn("Unknown endpoint");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
