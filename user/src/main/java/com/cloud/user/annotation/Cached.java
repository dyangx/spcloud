package com.cloud.user.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {

    /**
     * 缓存的名字
     */
    String name() default "";

    /**
     * 缓存 key
     * spEL 表达式 eg: Hello, #{ #name },#{#user.name}
     */
    String key() default "";

    /**
     * 失效时间，单位秒
     */
    long expiry() default 5*30;
}
