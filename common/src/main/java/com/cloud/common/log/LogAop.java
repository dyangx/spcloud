package com.cloud.common.log;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author: yangjie
 * @date: Created in 2020/5/13
 */

@Log4j
@Component
@Aspect
public class LogAop {

    private static String k = "123456";
//
//    @Around(value = "${aasdf}")
//    public void processRst(ProceedingJoinPoint point){
//
//    }

}
