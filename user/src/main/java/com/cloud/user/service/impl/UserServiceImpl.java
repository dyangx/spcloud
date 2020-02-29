package com.cloud.user.service.impl;

import com.cloud.user.mapper.UserMapper;
import com.cloud.user.service.UerService;
import com.cloud.user.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UserServiceImpl implements UerService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectUser() {
        return userMapper.selectUser();
    }

//    @PostConstruct
    public void test(){
        List<User> selectUser = userMapper.selectUser();
        System.out.println(selectUser);
        User u = selectUser.get(0);
    }
}
