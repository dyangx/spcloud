package com.cloud.user.service;

import org.apache.lucene.util.RamUsageEstimator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author: yangjie
 * @date: Created in 2020/5/27 16:34
 */
@Component
public class TestComp {
    static ArrayList<byte[]> b = new ArrayList<>();

    public void end() throws InterruptedException {
        System.out.println("start");
        Integer in = 0;
        while (true){
            in += 1;
            b.add(new byte[1024*1024*10]);
            String s = ""+in;
            b.add(new byte[2048]);
            Thread.sleep(1000);
            System.out.println("index: " +in +" 占用内存： "+RamUsageEstimator.humanSizeOf(b));
        }
    }
}
