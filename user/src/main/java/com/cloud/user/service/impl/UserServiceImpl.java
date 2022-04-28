package com.cloud.user.service.impl;

import com.cloud.user.aspect.CachedAspect;
import com.cloud.user.mapper.UserMapper;
import com.cloud.user.service.UerService;
import com.cloud.user.util.CachedUtil;
import com.cloud.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UerService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<User> selectUser() {
        return userMapper.selectUser();
    }

    @Override
    public Long deleteCache() {
        Set<String> cachedNames = CachedUtil.getCachedNameFromPath(CachedAspect.cachedPath);
        // 缓存所有的key
        Set<String> keys = new HashSet<>();
        for(String name : cachedNames){
            // 模糊匹配缓存名字
            keys.addAll(redisTemplate.keys(name + "*"));
        }
        //删除缓存
        long deleted = redisTemplate.delete(keys);
        System.out.println(deleted);
        return deleted;
    }

    @Override
//    @Transactional
    public void testUser() {
        userMapper.getOneUser();
        userMapper.getOneUser();
    }

    //    @PostConstruct
    public void test(){
        List<User> selectUser = userMapper.selectUser();
        System.out.println(selectUser);
        User u = selectUser.get(0);
    }
}
