package com.kalu.asmplugin.annotation;

import androidx.annotation.Keep;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: 快速点击
 * created by kalu on 2021-01-19
 */
@Keep
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FastClick {

    long time() default 1000;
}
