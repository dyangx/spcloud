package com.cloud.log.controller;

import com.cloud.log.biz.ParamBiz;
import com.cloud.log.dto.ReqDto;
import com.cloud.log.vo.Page;
import com.cloud.log.vo.RequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: yangjie
 * @date: Created in 2020/4/15
 */
@RestController
@RequestMapping("/spLog")
public class CommonController {

    @Autowired
    private ParamBiz paramBiz;

    @RequestMapping("/find")
    public Page<RequestVO> find(@RequestBody ReqDto dto){
        return paramBiz.find(dto);
    }

}
