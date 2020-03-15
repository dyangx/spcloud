package com.cloud.shop.controller;

import com.cloud.shop.service.LcnShopService;
import com.cloud.shop.service.impl.LcnShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lcnShop2")
public class LcnShopController {

    @Autowired
    private LcnShopService lcnShopService;

    @RequestMapping("insertValue")
    public String inserValue(){
        return lcnShopService.insrtValue();
    }
}
