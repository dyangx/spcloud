package com.cloud.user.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {

    String name() default "";

    String key() default "";

    long expiry() default 5*30;
}
