package com.cloud.user.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.user.annotation.Cached;
import com.cloud.user.service.UerService;
import com.cloud.user.util.CachedUtil;
import com.cloud.user.util.ClassUtil;
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

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class CachedAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UerService uerService;

    public static final String[] cachedPath = new String[]{"com.cloud.user.feign"};

    /**
     * 启动清除缓存
     */
    @PostConstruct
    public void clearCache(){
        uerService.deleteCache();
    }

    @Around("execution(public * com.cloud.user.feign.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 方法签名
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Cached cached = method.getDeclaredAnnotation(Cached.class);
        if(cached == null){
            return point.proceed();
        }
        String name = CachedUtil.getCachedName(cached,method,point.getArgs());
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
}
