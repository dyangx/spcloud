package com.cloud.movie.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/getHtml")
    public ResponseEntity<String> sendError(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.err.println(session.getId());
        System.err.println(session.getAttribute("abcd"));
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_HTML);
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>提示</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h3>你好好噢噢噢噢</h3>\n" +
                "<a href=\"javascript:history.back(-1)\">返回</a>\n" +
                "</body>\n" +
                "</html>";
        return new ResponseEntity<>(html, header, HttpStatus.OK);
    }

}
