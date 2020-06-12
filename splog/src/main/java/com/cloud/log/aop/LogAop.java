package com.cloud.log.aop;

import com.cloud.log.biz.ParamBiz;
import com.cloud.log.util.JacksonUtil;
import com.cloud.log.vo.RequestVO;
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
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Aspect
@Component
@Order(100)
public class LogAop {

    @Autowired
    private ParamBiz paramBiz;

    //execution(public * com.cloud.*.controller.*.*(..))
    @Pointcut("bean(*Controller) && !bean(basicErrorController) && !bean(spLogController)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object value = null;
        Throwable e = null;
        RequestVO vo = HandRequestParam(point);
        try {
            value = point.proceed(point.getArgs());
        } catch (Throwable throwable) {
            e = throwable;
            throw throwable;
        } finally {
            paramBiz.handle(value,e,vo);
        }
        return value;
    }

    public static RequestVO HandRequestParam(ProceedingJoinPoint point){
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        Object[] args = point.getArgs();
        List<Object> params = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            Object object = args[i];
            if (object instanceof HttpServletResponse) {
                continue;
            }
            if (object instanceof HttpServletRequest) {
                continue;
            }
            params.add(object);
        }
        String methodName = point.getSignature().getName();
        String reqParam = JacksonUtil.toJsonString(params);
        RequestVO vo = RequestVO.builder().method(methodName)
                .method_description(point.getSignature().getDeclaringTypeName())
                .req_param(reqParam).exception((byte) 0)
                .time(new Date())
                .req_url(request.getRequestURI()).build();
        return vo;

    }
}
