package com.cloud.log.aop;

import com.cloud.log.biz.ParamBiz;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Order(100)
public class LogAop {

    @Autowired
    private ParamBiz paramBiz;

    //execution(public * com.cloud.*.controller.*.*(..))
    @Pointcut("bean(*Controller)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        long start = System.currentTimeMillis();
        Object value = null;
        Throwable e = null;
        try {
            value = point.proceed(point.getArgs());
        } catch (Throwable throwable) {
            e = throwable;
            throw throwable;
        } finally {
            paramBiz.handle(start,point,value,request,e);
        }
        return value;
    }
}
