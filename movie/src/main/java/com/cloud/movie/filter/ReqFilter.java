package com.cloud.movie.filter;

import com.cloud.movie.thread.SelfThread;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@ServletComponentScan
@WebFilter()
public class ReqFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        if(SelfThread.threadLocal.get() == null){
//            System.err.println("filter1--->"+SelfThread.threadLocal.get());
//            SelfThread.threadLocal.set(new byte[1024*1024*200]);
//        }
//        HttpServletRequest req = (HttpServletRequest) servletRequest ;
//        System.out.println(Thread.currentThread().getName());
//        System.err.println("filter2--->"+req.getMethod()+SelfThread.threadLocal.get());
//        System.out.println(req.getRequestURI());
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
