package com.example.cjh.handler;

import com.example.cjh.uitls.UserThreadLocal;
import com.example.csl.bean.FsUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        FsUser fsUser=new FsUser();
        fsUser.setUserId(1L);
        UserThreadLocal.put(fsUser);
        System.out.println("eeee");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
