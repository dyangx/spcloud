package com.cloud.order.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 统一日志处理切面
 * @author yangx
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 定义切点：拦截controller包下的所有方法
     */
    @Pointcut("execution(* com.cloud.order.controller..*(..))")
    public void controllerPointcut() {}

    /**
     * 环绕通知：记录方法执行前后的日志
     */
    @Around("controllerPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        // 记录请求开始日志
        logRequestStart(request, className, methodName, args);
        
        Object result = null;
        Exception exception = null;
        
        try {
            // 执行目标方法
            result = joinPoint.proceed();
            return result;
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            // 记录请求结束日志
            logRequestEnd(request, className, methodName, result, exception, executionTime);
        }
    }

    /**
     * 记录请求开始日志
     */
    private void logRequestStart(HttpServletRequest request, String className, String methodName, Object[] args) {
        try {
            String requestInfo = "";
            if (request != null) {
                requestInfo = String.format("URL: %s, Method: %s, IP: %s", 
                    request.getRequestURL().toString(),
                    request.getMethod(),
                    getClientIpAddress(request));
            }
            
            String argsJson = "[]";
            if (args != null && args.length > 0) {
                // 过滤掉HttpServletRequest等不需要序列化的参数
                Object[] filteredArgs = Arrays.stream(args)
                    .filter(arg -> arg != null && 
                           !arg.getClass().getName().startsWith("javax.servlet") &&
                           !arg.getClass().getName().startsWith("org.springframework"))
                    .toArray();
                
                if (filteredArgs.length > 0) {
                    argsJson = objectMapper.writeValueAsString(filteredArgs);
                }
            }
            
            log.info("==> 请求开始 | {}#{} | {} | 入参: {}", 
                className, methodName, requestInfo, argsJson);
                
        } catch (Exception e) {
            log.warn("记录请求开始日志失败: {}", e.getMessage());
        }
    }

    /**
     * 记录请求结束日志
     */
    private void logRequestEnd(HttpServletRequest request, String className, String methodName, 
                              Object result, Exception exception, long executionTime) {
        try {
            if (exception != null) {
                log.error("<== 请求异常 | {}#{} | 耗时: {}ms | 异常: {}", 
                    className, methodName, executionTime, exception.getMessage());
            } else {
                String resultJson = "null";
                if (result != null) {
                    resultJson = objectMapper.writeValueAsString(result);
                    // 如果返回结果太长，截断显示
                    if (resultJson.length() > 1000) {
                        resultJson = resultJson.substring(0, 1000) + "...";
                    }
                }
                
                log.info("<== 请求结束 | {}#{} | 耗时: {}ms | 出参: {}", 
                    className, methodName, executionTime, resultJson);
            }
        } catch (Exception e) {
            log.warn("记录请求结束日志失败: {}", e.getMessage());
        }
    }

    /**
     * 获取客户端真实IP地址
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
}