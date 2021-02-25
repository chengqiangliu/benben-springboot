package com.benben.annotation;

import com.benben.global.constants.ApiName;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BenbenApiEndpoint {

    /**
     * returns apiName
     *
     * @return apiName
     */
    ApiName apiName() default ApiName.UNKNOWN;

    /**
     * signifies if authentication is needed or not
     *
     * @return boolean
     */
    boolean authentication() default true;
}
