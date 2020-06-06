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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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

    @Around("execution(public * com.cloud.user.feign.*.*(..))")
    public Object around(ProceedingJoinPoint point){
        // 方法签名
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Cached cached = method.getDeclaredAnnotation(Cached.class);
        try {
            if(cached == null){
                return point.proceed();
            }
            String name = cached.name();
            long expiry = cached.expiry();
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

}
