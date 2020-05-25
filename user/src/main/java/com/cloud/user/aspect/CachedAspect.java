package com.cloud.user.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.user.annotation.Cached;
import com.cloud.user.vo.User;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class CachedAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Around("execution(public * com.cloud.user.feign.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 方法签名
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Cached cached = method.getDeclaredAnnotation(Cached.class);
        if(cached == null){
            return point.proceed();
        }
        String name = getCachedName(cached,method,point.getArgs());
        String s = stringRedisTemplate.opsForValue().get(name);
        if(s != null){
            return JSONObject.parseObject(s,Object.class);
        }
        Object object = point.proceed();
        String res = JSON.toJSONString(object);
        long expiry = cached.expiry();
        stringRedisTemplate.opsForValue().set(name, res, expiry, TimeUnit.SECONDS);
        return object;
    }

    private static String getCachedName(Cached cached,Method method,Object[] args){
        // spel 解析
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        Expression expression = parser.parseExpression(cached.key(),new TemplateParserContext());
        Parameter[] parameters = method.getParameters();
        for(int i=0;i<parameters.length;i++){
            context.setVariable(parameters[i].getName(),args[i]);
        }
        String key_ = expression.getValue(context, String.class);
        if(StringUtils.isNotEmpty(cached.name())){
            return cached.name() + "::" +key_;
        }
        return key_;
    }

}
