package com.cloud.user.service.impl;

import com.cloud.user.feign.MoviefeignClient;
import com.cloud.user.feign.ShopFeignClient;
import com.cloud.user.mapper.LcnUserMapper;
import com.cloud.user.service.LcnUserService;
import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.core.DTXLocalContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LcnUserServiceImpl implements LcnUserService {

    @Resource
    private LcnUserMapper lcnUserMapper;

    @Autowired
    private ShopFeignClient shopFeignClient;

    @Autowired
    private MoviefeignClient moviefeignClient;

    @Override
    @LcnTransaction
    public String insertValue(String go) {
        String shop = shopFeignClient.insertValue();
        String movie = moviefeignClient.insert();
        lcnUserMapper.insetValue();
        System.err.println(shop);
        System.err.println(movie);
        System.err.println(DTXLocalContext.getOrNew().getGroupId() + Transactions.APPLICATION_ID_WHEN_RUNNING);
        if(go == null){
            throw new RuntimeException("回滚！！！！！！");
        }
        return "lcn-user-ok !";
    }
}
