package com.cloud.movie.service.impl;

import com.cloud.movie.mapper.test1.LcnMovieMapper;
import com.cloud.movie.service.LcnMovieService;
import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.TxcTransaction;
import com.codingapi.txlcn.tc.core.DTXLocalContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class LcnMovieServiceImpl implements LcnMovieService {

    private LcnMovieMapper lcnMovieMapper;

    @Resource
    void setServie(LcnMovieMapper lcnMovieMapper){
        this.lcnMovieMapper = lcnMovieMapper;
    }

    @Override
    @Transactional( transactionManager = "manager1")
    @TxcTransaction(propagation = DTXPropagation.SUPPORTS)
    public String insertValue() {
        lcnMovieMapper.insert();
        System.err.println(DTXLocalContext.getOrNew().getGroupId() + Transactions.APPLICATION_ID_WHEN_RUNNING);

        return "lcn-movie-ok!";
    }
}
