package com.cloud.user.controller;

import com.cloud.user.service.LcnUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lcnUser")
public class LcnUserController {

    @Autowired
    private LcnUserService lcnUserService;

    @RequestMapping("/insert")
    public String insertValue(String go){
        return lcnUserService.insertValue(go);
    }
}
