package com.cloud.shop.controller;

import com.cloud.shop.service.LcnShopService;
import com.cloud.shop.service.impl.LcnShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lcnShop")
public class LcnShopController {

    @Autowired
    private LcnShopService lcnShopService;

    @RequestMapping("/inserValue")
    public String inserValue(){
        return lcnShopService.insrtValue();
    }
}
