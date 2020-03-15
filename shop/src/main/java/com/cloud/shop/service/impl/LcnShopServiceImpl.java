package com.cloud.shop.service.impl;

import com.cloud.shop.mapper.LcnShopMapper;
import com.cloud.shop.service.LcnShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

@Service("sshop")
public class LcnShopServiceImpl implements LcnShopService {

    private LcnShopMapper lcnShopMapper;

    @Autowired
    void setService(LcnShopMapper lcnShopMapper){
        this.lcnShopMapper = lcnShopMapper;
    }
    @Override
//    @TxcTransaction(propagation = DTXPropagation.SUPPORTS)
    public String insrtValue() {
        lcnShopMapper.insert();
//        System.out.println(TracingContext.tracing().groupId());
        return "lcn-shop-ok !";
    }

/*    public void confirmRpc(String value) {
        ids.remove(TracingContext.tracing().groupId());
    }

    public void cancelRpc(String value) {
        Long kid = ids.get(TracingContext.tracing().groupId());
        System.err.println(kid);
    }*/
}
