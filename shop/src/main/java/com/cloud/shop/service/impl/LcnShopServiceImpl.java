package com.cloud.shop.service.impl;

import com.cloud.shop.mapper.LcnShopMapper;
import com.cloud.shop.service.LcnShopService;
import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.codingapi.txlcn.tc.core.DTXLocalContext;
import com.codingapi.txlcn.tracing.TracingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LcnShopServiceImpl implements LcnShopService {

    @Resource
    private LcnShopMapper lcnShopMapper;

    private ConcurrentHashMap<String, Long> ids = new ConcurrentHashMap<>();

    @Override
    @TxcTransaction(propagation = DTXPropagation.SUPPORTS)
    @Transactional
    public String insrtValue() {
        lcnShopMapper.insert();
        System.err.println(DTXLocalContext.getOrNew().getGroupId() + Transactions.getApplicationId());
        return "lcn-shop-ok !";
    }

    public void confirmRpc(String value) {
        ids.remove(TracingContext.tracing().groupId());
    }

    public void cancelRpc(String value) {
        Long kid = ids.get(TracingContext.tracing().groupId());
//        demoMapper.deleteByKId(kid);
        System.out.println(kid);
    }
}
