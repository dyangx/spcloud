package com.cloud.rabbit.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * @author Lenovo
 * redis序列化工具
 * @param <T>
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
	 
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
 
 
 
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONBytes(t, SerializerFeature.WriteClassName);
    }
 
  
	@Override
	@SuppressWarnings("unchecked")
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
		String str = new String(bytes, DEFAULT_CHARSET);
        ParserConfig parserConfig=new ParserConfig();
        parserConfig.setAutoTypeSupport(true);
        return (T) JSON.parse(str,parserConfig);
    }
 
}

