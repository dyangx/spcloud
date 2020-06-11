package com.cloud.log.util;

import com.cloud.log.vo.RequestVO;

import java.util.Objects;

/**
 * @author: yangjie
 * @date: Created in 2020/6/9
 */
public class LogUtil {

    public static void print(RequestVO vo){
        StringBuffer sb = new StringBuffer("");
        sb.append("请求路径："+vo.getReq_url());
        sb.append(lightYourCode("请求参数：",vo.getReq_param()));
        if(Objects.equals(vo.getException(),(byte)0)){
            sb.append(lightYourCode("返回参数：",vo.getRes_param()));
        }else {
            sb.append("\n\033[0;31m 返回参数："+ "Exception \033[0m");
        }
        sb.append("\n耗时："+vo.getExec_time());
        System.out.println(sb.toString());
    }

    private static String lightYourCode(String head,String body){
        StringBuffer sb = new StringBuffer("\n\033[0;30;45m");
        sb.append(head).append("\033[0m \n\033[0;92m").append(body).append("\033[0m");
        return sb.toString();
    }
}
