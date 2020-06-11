package com.cloud.log.biz;

import com.cloud.log.dto.ReqDto;
import com.cloud.log.util.*;
import com.cloud.log.vo.Page;
import com.cloud.log.vo.RequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: yangjie
 * @date: Created in 2020/6/9
 */
@Component
public class ParamBiz {

    @Value("${sp.log.print-info}")
    private Boolean print;

    @Value("${sp.log.mongo.save}")
    private Boolean save;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Async
    public void handle(Object value, Throwable e,RequestVO vo){
        String resParam = JacksonUtil.toJsonString(value);
        Long execTime = System.currentTimeMillis() - vo.getTime().getTime();
        vo.setRes_param(resParam);
        vo.setExec_time(execTime);
        if(e != null){
            vo.setException((byte) 1);
            String exception = ExceptionUtil.writeAsString(e);
            vo.setException_description(exception);
        }
        if(print){
            LogUtil.print(vo);
        }
        if(save){
            mongoTemplate.insert(vo);
        }
    }

    public Page<RequestVO> find(ReqDto dto){
        MongoQuery mongoQuery = MongoQuery.create()
                .is("method",dto.getMethod())
                .gt("time",DateUtil.toUTC(dto.getStartTime()));
        List<RequestVO> list = mongoTemplate.find(mongoQuery,RequestVO.class);
        System.out.println(list);

        return new Page<RequestVO>(list);
    }

}
