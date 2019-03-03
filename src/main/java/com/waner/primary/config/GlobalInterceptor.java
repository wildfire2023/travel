package com.waner.primary.config;

import com.waner.primary.web.entity.SysUser;
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
        // 主页放过
        if ("/".equals(uri)) {
            return true;
        }
        if (uri.contains("front") || uri.contains("user")||uri.contains("file")) {
            return true;
        }
        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sessionUser");
        if (sysUser != null) {
            return true;
        }
        request.getRequestDispatcher("/front/page/login").forward(request,response);
        return false;
    }
}
