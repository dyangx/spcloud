package com.cloud.log.biz;

import com.cloud.log.util.ExceptionUtil;
import com.cloud.log.util.JacksonUtil;
import com.cloud.log.util.LogUtil;
import com.cloud.log.vo.RequestVO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: yangjie
 * @date: Created in 2020/6/9 9:49
 */
@Component
public class ParamBiz {

    @Value("${sp.log.print-info}")
    private Boolean print;

    @Async
    public void handle(Long startTime, ProceedingJoinPoint point, Object value, HttpServletRequest request,Throwable e){
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
        String resParam = JacksonUtil.toJsonString(value);
        Long execTime = System.currentTimeMillis() - startTime;
        RequestVO vo = RequestVO.builder().method(methodName)
                .methodDescription(point.getSignature().getDeclaringTypeName())
                .reqParam(reqParam).exception(0)
                .startTime(new Date(startTime))
                .execTime(execTime).reqtUrl(request.getRequestURI())
                .resParam(resParam).build();
        if(e != null){
            vo.setException(1);
            String exception = ExceptionUtil.writeAsString(e);
            vo.setExceptionDescription(exception);
        }
        if(print){
            LogUtil.print(vo);
        }
    }


}
