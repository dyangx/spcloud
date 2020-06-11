package com.cloud.log.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author: yangjie
 * @date: Created in 2020/6/9
 */
public class JacksonUtil {

    /** 配置jackson */
    private static final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    public static String toJsonString(Object object){
        if(object == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("jackson转换出错！");
        }
    }

    public static <T> T toJavaObject(String jsonString,Class<T> clazz){
        try {
            return objectMapper.readValue(jsonString,clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("String转换成Object出错！");
        }
    }

}
