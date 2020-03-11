package com.cloud.movie.service.impl;

import com.cloud.movie.mapper.test1.LcnMovieMapper;
import com.cloud.movie.service.LcnMovieService;
import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.codingapi.txlcn.tc.core.DTXLocalContext;
import com.codingapi.txlcn.tracing.TracingContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LcnMovieServiceImpl implements LcnMovieService {

    private LcnMovieMapper lcnMovieMapper;

    private ConcurrentHashMap<String, Set<Long>> ids = new ConcurrentHashMap<>();

    @Resource
    void setServie(LcnMovieMapper lcnMovieMapper){
        this.lcnMovieMapper = lcnMovieMapper;
    }

    @Override
//    @Transactional( transactionManager = "manager1")
    @TccTransaction(propagation = DTXPropagation.SUPPORTS)
    @Transactional
    public String insertValue() {
        lcnMovieMapper.insert();
        System.err.println(DTXLocalContext.getOrNew().getGroupId() + Transactions.APPLICATION_ID_WHEN_RUNNING);

        return "lcn-movie-ok!";
    }


    public void confirmRpc(String value) {
        ids.get(TracingContext.tracing().groupId()).forEach(id -> {
            System.out.println(id);
            ids.get(TracingContext.tracing().groupId()).remove(id);
        });
    }

    public void cancelRpc(String value) {
        ids.get(TracingContext.tracing().groupId()).forEach(id -> {
            System.out.println(id);
        });
    }
}
