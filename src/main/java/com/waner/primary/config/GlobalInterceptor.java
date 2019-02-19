package com.waner.primary.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 全局拦截器
 * @author Monster
 * @date 2019/2/13 11:14
 * @since 1.8
 */
@Component
public class GlobalInterceptor extends HandlerInterceptorAdapter {


    /**
     * 前置处理器
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        HttpSession session = request.getSession();

        return super.preHandle(request, response, handler);
    }
}
