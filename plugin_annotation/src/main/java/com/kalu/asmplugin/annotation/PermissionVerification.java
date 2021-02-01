package com.kalu.asmplugin.annotation;

import androidx.annotation.Keep;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: 运行时权限
 * created by kalu on 2021-01-25
 */
@Keep
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionVerification {

    String[] requestPermissions() default {};

    int requestCode() default -1;

    Class<?> requestCall();

    Class<?> requestSuperCall();
}
