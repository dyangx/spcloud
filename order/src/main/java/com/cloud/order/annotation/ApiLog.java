package com.cloud.order.annotation;

import java.lang.annotation.*;

/**
 * API日志注解
 * 用于标记需要记录日志的方法
 * @author yangx
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLog {
    
    /**
     * 操作描述
     */
    String value() default "";
    
    /**
     * 是否记录入参
     */
    boolean logArgs() default true;
    
    /**
     * 是否记录出参
     */
    boolean logResult() default true;
    
    /**
     * 是否记录执行时间
     */
    boolean logTime() default true;
}