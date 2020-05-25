package com.cloud.user.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.user.annotation.Cached;
import com.cloud.user.vo.User;
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
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class CachedAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private SourceLocation sourceLocation;

    @Around("execution(public * com.cloud.user.feign.*.*(..))")
    public Object around(ProceedingJoinPoint point){
        try {
            // 方法签名
            MethodSignature ms = (MethodSignature) point.getSignature();
            Method method = ms.getMethod();
            Cached cached = method.getDeclaredAnnotation(Cached.class);
            if(cached == null){
                return point.proceed();
            }
            String name = getName(cached,method,point.getArgs());
            long expiry = cached.expiry();
            for(Object o: method.getParameters()){
                System.out.println(o);
            }
            String s = stringRedisTemplate.opsForValue().get(name);
            if(s != null){
                return JSONObject.parseObject(s,Object.class);
            }
            Object object = point.proceed();
            String res = JSON.toJSONString(object);
            stringRedisTemplate.opsForValue().set(name, res, expiry, TimeUnit.SECONDS);
            return object;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;

    }

    private static String getName(Cached cached,Method method,Object[] args){
        String key = cached.key();
        // spel 解析
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        Expression expression = parser.parseExpression(cached.key(),new TemplateParserContext());
        expression.getValue(context, String.class);
        return null;

    }

}
