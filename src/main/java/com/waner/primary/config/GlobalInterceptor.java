package com.waner.primary.config;

import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局拦截器
 * @author Monster
 * @date 2019/2/13 11:14
 * @since 1.8
 */
public class GlobalInterceptor extends WebRequestHandlerInterceptorAdapter {
    public GlobalInterceptor(WebRequestInterceptor requestInterceptor) {
        super(requestInterceptor);
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return super.preHandle(request, response, handler);
    }
}
