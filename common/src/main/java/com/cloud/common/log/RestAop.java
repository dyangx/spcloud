//package com.cloud.common.log;
//
//import cn.rivamed.common.service.AccessTokenService;
//import cn.rivamed.common.vo.AppAccountInfoVo;
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.List;
//
//@Aspect
//@Component
//public class RestAop {
//
//    /**
//     * // 控制是否开启日志
//     */
//    public static Boolean onOff = false;
//
//    private static boolean isLinux = System.getProperty("os.name").toLowerCase().contains("linux");;
//
//    private static Logger logger = LoggerFactory.getLogger(RestAop.class);
//
//    @Resource
//    AccessTokenService accessTokenService;
//
//    @Pointcut("execution(public * cn.rivamed.*.web.rest..*.*(..))")
//    public void pointCutRest() {
//
//    }
//
//    @Pointcut("execution(public * cn.rivamed.*.mapper..*.*(..))")
//    public void pointCutMapper() {
//
//    }
//
//    @Pointcut("execution(public * cn.rivamed.*.service..*.*(..))")
//    public void pointCutService() {
//
//    }
//
//    @Around("pointCutRest()")
//    public Object processRst(ProceedingJoinPoint point) throws Throwable {
//        Object returnValue = null;
//        final List<Object> params = new ArrayList<>();
//        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = sra.getRequest();
//        AppAccountInfoVo vo = accessTokenService.getAppAccountInfoVo();
//        Object[] args = point.getArgs();
//        for (int i = 0; i < args.length; i++) {
//            Object object = args[i];
//            if (object instanceof HttpServletResponse) {
//                continue;
//            }
//            if (object instanceof HttpServletRequest) {
//                continue;
//            }
//            params.add(object);
//        }
//        String methodName = point.getSignature().getName();
//		if ("initBinder".equals(methodName) || "/base/account/rmApi/connect".equals(request.getRequestURI())) {
//			returnValue = point.proceed(point.getArgs());
//			return returnValue;
//		}
//        String cloneParams = "";
//        try {
//            cloneParams = JSONObject.toJSONString(params);
//        } catch (Exception e) {
//        }
//        logger.info("user info: " + JSONObject.toJSONString(vo));
//        logger.info("rest method: " + point.getSignature().getDeclaringTypeName() + ".>" + methodName);
//        logger.info(this.lightYourCode(methodName+" param:",cloneParams));
//        Long startTime = System.currentTimeMillis();
//        returnValue = point.proceed(point.getArgs());
//        Long endTime = System.currentTimeMillis();
//        ObjectMapper objectMapper = new ObjectMapper();
//        logger.info(this.lightYourCode(methodName + " result: ",objectMapper.writeValueAsString(returnValue)));
//        logger.info(request.getRequestURI() + "---used time---" + (endTime - startTime));
//        return returnValue;
//    }
//
//    private String lightYourCode(String head,String body){
//        if(isLinux){
//            return head + body;
//        }
//        StringBuffer sb = new StringBuffer("\033[0;30;45m");
//        sb.append(head).append("\033[0m \n\033[0;92m").append(body).append("\033[0m");
//        return sb.toString();
//    }
//}
