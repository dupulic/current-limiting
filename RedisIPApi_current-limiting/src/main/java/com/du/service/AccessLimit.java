package com.du.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//自定义注解
@Retention(RetentionPolicy.RUNTIME) //用来确定这个注解的生命周期
@Target(ElementType.METHOD) //注解使用的目标范围（类、方法、字段等）
public @interface AccessLimit {
    int seconds();
    int maxCount();
    boolean needLogin() default true;
}
