package com.cloud.log.util;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author: yangj
 * @date: Created in 2020/6/11
 */
public class MongoQuery extends Query {

    public static MongoQuery create(){
        return new MongoQuery();
    }

    public Criteria getLastCriteria(String constant){
        if(this.getCriteria().isEmpty()){
            super.addCriteria(Criteria.where(constant));
            constant = null;
        }
        List<CriteriaDefinition> list =  this.getCriteria();
        Criteria criteria = (Criteria) this.getCriteria().get(list.size() -1);
        if(constant != null){
            criteria = new Criteria(constant);
            super.addCriteria(criteria);
        }
        return criteria;

    }

    public MongoQuery is(String constant,Object value){
        this.getLastCriteria(constant).is(value);
        return this;
    }

    public MongoQuery lt(String constant,Object value){
        this.getLastCriteria(constant).lt(value);
        return this;
    }

    public MongoQuery gt(String constant,Object value){
        this.getLastCriteria(constant).gt(value);
        return this;
    }
}
