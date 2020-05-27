package com.cloud.user.util;

import com.cloud.user.annotation.Cached;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: yangjie
 * @date: Created in 2020/5/27 14:55
 */
public class CachedUtil {
    /**
     * 获取指定路径下所有类的方法里chached注解的name
     */
    public static Set<String> getCachedNameFromPath(String[] filePath){
        // 指定路径下的class
        Set<Class> sets = new HashSet<>();
        for(String s : filePath){
            sets.addAll(ClassUtil.getClasses(s));
        }
        // sets下的缓存名字
        Set<String> cachedNames = new HashSet<>();
        for(Class clazz : sets){
            // 类下的所有缓存名字
            cachedNames.addAll(getCachedName(clazz));
        }
        return cachedNames;
    }

    /**
     * 生成存放redis的key
     */
    public static String getCachedName(Cached cached, Method method, Object[] args){
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
            return cached.name() + ":" +key_;
        }
        return key_;
    }

    /**
     * 获取cached注解上缓存名字
     */
    private static Set<String> getCachedName(Class clazz){
        Set<String> cachedNames = new HashSet<>();
        // 类方法
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods){
            Cached cached = method.getDeclaredAnnotation(Cached.class);
            if(cached != null){
                cachedNames.add(cached.name());
            }
        }
        return cachedNames;
    }
}
