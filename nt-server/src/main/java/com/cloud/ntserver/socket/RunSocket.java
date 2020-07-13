package com.cloud.ntserver.socket;

import com.cloud.ntserver.server.PushServer;

/**
 * @author: yangj
 * @date: Created in 2020/7/13
 */
public class RunSocket implements Runnable {

    private RunSocket(){}

    private PushServer pushServer;

    public RunSocket(PushServer pushServer){
        this.pushServer = pushServer;
    }

    @Override
    public void run() {
        while (true){
            pushServer.push();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
