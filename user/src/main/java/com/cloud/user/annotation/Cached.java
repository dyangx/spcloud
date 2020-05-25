package com.cloud.user.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {

    String name() default "";

    long expiry() default 5*30;
}
