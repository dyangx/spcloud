package com.cloud.shop.service.impl;

import com.cloud.shop.mapper.LcnShopMapper;
import com.cloud.shop.service.LcnShopService;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.codingapi.txlcn.tracing.TracingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

@Service("sshop")
public class LcnShopServiceImpl implements LcnShopService {

    private LcnShopMapper lcnShopMapper;

    private ConcurrentHashMap<String, Long> ids = new ConcurrentHashMap<>();

    @Autowired
    void setService(LcnShopMapper lcnShopMapper){
        this.lcnShopMapper = lcnShopMapper;
    }

    @Override
    @LcnTransaction
    @Transactional
    public String insrtValue() {
        lcnShopMapper.insert();
        System.out.println(TracingContext.tracing().groupId());
        return "lcn-shop-ok !";
    }
}
