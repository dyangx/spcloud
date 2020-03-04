package com.cloud.movie.service.impl;

import com.cloud.movie.mapper.test1.TestMapper;
import com.cloud.movie.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper1;

    @Autowired
    private com.cloud.movie.mapper.test2.TestMapper testMapper2;


    @Override
    @Transactional( transactionManager = "manager2")
    public void test() {
        testMapper1.insertValue1();
        testMapper2.insertValue2();

        throw new RuntimeException("你错了！");
    }
}
