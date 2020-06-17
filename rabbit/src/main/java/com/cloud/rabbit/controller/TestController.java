package com.cloud.rabbit.controller;

import com.cloud.rabbit.redis.SetNxLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: yangj
 * @date: Created in 2020/6/17
 */
@RestController()
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SetNxLock setNxLock;

    @RequestMapping("/getLock")
    @ResponseBody
    public String getLock() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1000);
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicInteger count = new AtomicInteger();
        long start = System.currentTimeMillis();
        for (int i = 0;i<1000;i++){
            executorService.execute(() -> {
                setNxLock.getLock("myLock",10);
                countDownLatch.countDown();
                count.getAndIncrement();
            });
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        String res = "总耗时:"+(end-start)+"  count数为:"+count;
        return res;
    }

    @RequestMapping("/getSingleLock")
    @ResponseBody
    public String getSingleLock() throws InterruptedException {
        setNxLock.getLockAndDo();
        return "ok";
    }
}
