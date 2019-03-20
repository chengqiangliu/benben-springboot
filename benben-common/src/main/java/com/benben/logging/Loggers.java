package com.benben.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
public class Loggers {

    /** Http logger */
    public static Logger HTTP_LOGGER = LoggerFactory.getLogger("Http");

    /** api logger */
    public static Logger API_LOGGER = LoggerFactory.getLogger("BenbenAPI");

    /** Service logger */
    public static Logger SERVICE_LOGGER = LoggerFactory.getLogger("BenbenService");

    /** Dao logger */
    public static Logger DAO_LOGGER = LoggerFactory.getLogger("BenbenDao");

    /** Rabbitmq logger */
    public static Logger RABBITMQ_LOGGER = LoggerFactory.getLogger("BenbenRabbitmq");

    /** Worke logger */
    public static Logger WORKER_LOGGER = LoggerFactory.getLogger("BenbenWorker");

    /** Performance logger */
    public static Logger PERFORMANCE = LoggerFactory.getLogger("Performance");

    /** Auth logger */
    public static Logger AUTH = LoggerFactory.getLogger("Auth");

    /** API_CLIENT logger */
    public static Logger API_CLIENT = LoggerFactory.getLogger("BenbenApiClient");

    /** BATCH logger */
    public static Logger BATCH = LoggerFactory.getLogger("BenbenBatch");

    /** Test logger */
    public static Logger TEST = LoggerFactory.getLogger("Test");

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJsonString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "";
        }
    }

    public static void performance(Long startTime, Long endTime, Long threshold) {
        Long totalTime = endTime - startTime;
        if (PERFORMANCE.isDebugEnabled() || totalTime > threshold) {

            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            String className = stackTraceElements[2].getClassName();
            String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
            String methodName = stackTraceElements[2].getMethodName();

            if (totalTime > threshold) {
                PERFORMANCE.warn(LoggerFormat.PERFORMANCE, totalTime, simpleClassName, methodName);
            } else {
                PERFORMANCE.debug(LoggerFormat.PERFORMANCE, totalTime, simpleClassName, methodName);
            }

        }
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable rootCause = t;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }
}
