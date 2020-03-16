package com.cloud.user.controller;

import com.cloud.user.feign.MoviefeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    private MoviefeignClient moviefeignClient;

    @RequestMapping("/getHtml")
    public ResponseEntity<String> getHtml(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.err.println(session.getId());
        session.setAttribute("abcd","123456789");
        return moviefeignClient.getHtml();
    }
}
