package com.cloud.log.biz;

import com.cloud.log.vo.Page;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: yangj
 * @date: Created in 2020/6/12
 */

@Component
public class MongoTemplateBiz {

    @Autowired
    private MongoTemplate mongoTemplate;

    public <T> Page<T> findPage(Page<T> page, Query query,Class<T> clazz){
        if(page != null){
            if(page.isSearchCount()){
                long count = mongoTemplate.count(query,clazz);
                page.setTotal(count);
            }
            query.skip((page.getCurrent()-1)*page.getSize());
            query.limit(page.getSize());
        }else {
            page = new Page<T>();
        }
        List<T> list = mongoTemplate.find(query,clazz);
        page.setRecord(list);
        if(page.getTotal() == 0){
            page.setTotal(list.size());
        }
        return page;
    }

}
