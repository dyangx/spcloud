package com.cloud.order.aspect;

import com.cloud.order.annotation.ApiLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

/**
 * API日志切面 - 支持注解配置
 * @author yangx
 */
@Slf4j
@Aspect
@Component
public class ApiLogAspect {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 定义切点：拦截带有@ApiLog注解的方法
     */
    @Pointcut("@annotation(com.cloud.order.annotation.ApiLog) || @within(com.cloud.order.annotation.ApiLog)")
    public void apiLogPointcut() {}

    /**
     * 环绕通知：记录API调用日志
     */
    @Around("apiLogPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 生成请求ID
        String requestId = UUID.randomUUID().toString().substring(0, 8);
        
        // 获取方法和注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ApiLog apiLog = getApiLogAnnotation(method, joinPoint.getTarget().getClass());
        
        if (apiLog == null) {
            return joinPoint.proceed();
        }
        
        long startTime = System.currentTimeMillis();
        
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = method.getName();
        Object[] args = joinPoint.getArgs();
        
        // 记录请求开始日志
        logApiStart(requestId, request, className, methodName, args, apiLog);
        
        Object result = null;
        Exception exception = null;
        
        try {
            result = joinPoint.proceed();
            return result;
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            // 记录请求结束日志
            logApiEnd(requestId, className, methodName, result, exception, executionTime, apiLog);
        }
    }

    /**
     * 获取ApiLog注解
     */
    private ApiLog getApiLogAnnotation(Method method, Class<?> targetClass) {
        // 先从方法上查找
        ApiLog apiLog = AnnotationUtils.findAnnotation(method, ApiLog.class);
        if (apiLog != null) {
            return apiLog;
        }
        
        // 再从类上查找
        return AnnotationUtils.findAnnotation(targetClass, ApiLog.class);
    }

    /**
     * 记录API请求开始日志
     */
    private void logApiStart(String requestId, HttpServletRequest request, String className, 
                            String methodName, Object[] args, ApiLog apiLog) {
        try {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("==> API请求开始 [").append(requestId).append("] ");
            
            // 操作描述
            if (!apiLog.value().isEmpty()) {
                logBuilder.append("| ").append(apiLog.value()).append(" ");
            }
            
            logBuilder.append("| ").append(className).append("#").append(methodName);
            
            // 请求信息
            if (request != null) {
                logBuilder.append(" | ").append(request.getMethod())
                         .append(" ").append(request.getRequestURI())
                         .append(" | IP: ").append(getClientIpAddress(request));
            }
            
            // 入参
            if (apiLog.logArgs() && args != null && args.length > 0) {
                Object[] filteredArgs = Arrays.stream(args)
                    .filter(arg -> arg != null && 
                           !arg.getClass().getName().startsWith("javax.servlet") &&
                           !arg.getClass().getName().startsWith("org.springframework"))
                    .toArray();
                
                if (filteredArgs.length > 0) {
                    String argsJson = objectMapper.writeValueAsString(filteredArgs);
                    logBuilder.append(" | 入参: ").append(argsJson);
                }
            }
            
            log.info(logBuilder.toString());
            
        } catch (Exception e) {
            log.warn("记录API请求开始日志失败: {}", e.getMessage());
        }
    }

    /**
     * 记录API请求结束日志
     */
    private void logApiEnd(String requestId, String className, String methodName, 
                          Object result, Exception exception, long executionTime, ApiLog apiLog) {
        try {
            StringBuilder logBuilder = new StringBuilder();
            
            if (exception != null) {
                logBuilder.append("<== API请求异常 [").append(requestId).append("] ")
                         .append("| ").append(className).append("#").append(methodName);
                
                if (apiLog.logTime()) {
                    logBuilder.append(" | 耗时: ").append(executionTime).append("ms");
                }
                
                logBuilder.append(" | 异常: ").append(exception.getClass().getSimpleName())
                         .append(" - ").append(exception.getMessage());
                
                log.error(logBuilder.toString());
            } else {
                logBuilder.append("<== API请求结束 [").append(requestId).append("] ")
                         .append("| ").append(className).append("#").append(methodName);
                
                if (apiLog.logTime()) {
                    logBuilder.append(" | 耗时: ").append(executionTime).append("ms");
                }
                
                // 出参
                if (apiLog.logResult() && result != null) {
                    String resultJson = objectMapper.writeValueAsString(result);
                    // 如果返回结果太长，截断显示
                    if (resultJson.length() > 500) {
                        resultJson = resultJson.substring(0, 500) + "...";
                    }
                    logBuilder.append(" | 出参: ").append(resultJson);
                }
                
                log.info(logBuilder.toString());
            }
            
        } catch (Exception e) {
            log.warn("记录API请求结束日志失败: {}", e.getMessage());
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