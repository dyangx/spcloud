package com.cloud.rabbit.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.apache.tomcat.util.net.IPv6Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import sun.net.util.IPAddressUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yangj
 * @date: Created in 2020/6/17
 */
@Component
public class SetNxLock {

    /** 锁 **/
    private static final String LOCK_KEY = "redis_lock";

    /** 锁过期时间 **/
    private static final int LOCK_EXPIRE_TIME = 5000;

    private static DefaultRedisScript<String> lockScript = getLockScript();
    private static DefaultRedisScript<String> unLockScript = getUnLockScript();

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /** 获取锁 */
    public boolean locked(String key,int timeout,String val){
        List<String> keyList = new ArrayList<>();
        keyList.add(key);
        keyList.add(String.valueOf(timeout));
        keyList.add(val);
        String result = stringRedisTemplate.execute(lockScript, keyList);
        if("ok".equals(result.toLowerCase())){
            return true;
        }
        return false;
    }

    /**
     * 释放锁
     */
    public String releaseLock(String key,String value){
        List<String> keyList = new ArrayList<>();
        keyList.add(key);
        keyList.add(value);
        String result = stringRedisTemplate.execute(unLockScript, keyList);
        return result;
    }

    public void getLockAndDo(){
        String address = "127.0.0.1:8080";
        boolean lock = false;
        try {
            lock = locked(LOCK_KEY,LOCK_EXPIRE_TIME,address);
            if(lock){
                System.err.println(Thread.currentThread().getName()+"->获取reids锁成功！");
            }else {
                String value = stringRedisTemplate.opsForValue().get(LOCK_KEY);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            /*if(lock){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stringRedisTemplate.delete(LOCK_KEY);
                releaseLock(LOCK_KEY,address);
            }*/
        }
    }

    /**
     * 获取锁
     * @param  lockKey
     * @param  timeout
     * @return
     */
    public boolean getLock(String lockKey,int timeout){
        boolean lock = locked(lockKey,timeout,lockKey);
        if(lock){
            System.err.println(Thread.currentThread().getName()+"->获取reids锁成功！");
        }
        return lock;
    }

    /**
     * 加载lua脚本
     */
    private static DefaultRedisScript<String> getLockScript(){
        DefaultRedisScript<String> lockScript_ = new DefaultRedisScript<>();
        lockScript_.setScriptSource(new ResourceScriptSource(new ClassPathResource("lock/addLock.lua")));
        lockScript_.setResultType(String.class);
        return lockScript_;
    }

    private static DefaultRedisScript<String> getUnLockScript(){
        DefaultRedisScript<String> unLockScript_ = new DefaultRedisScript<>();
        unLockScript_.setScriptSource(new ResourceScriptSource(new ClassPathResource("lock/unlock.lua")));
        unLockScript_.setResultType(String.class);
        return unLockScript_;
    }
}
