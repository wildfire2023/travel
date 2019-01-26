package com.waner.primary.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>页面请求控制器</p>
 * @author Monster
 * @date 2019/1/10 16:42
 * @since 1.8
 */
@Controller
@RequestMapping("page")
public class PageController {

    /**
     * 跳转后台控制台页面
     * @return
     */
    @RequestMapping("console")
    public String toConsolePage() {
        return "background/home/console";
    }

    /**
     * 跳转登录页面
     * @return
     */
    @RequestMapping("login")
    public String login() {
        return "background/user/login";
    }

    /**
     * 跳转注册页面
     * @return
     */
    @RequestMapping("register")
    public String register() {
        return "background/user/reg";
    }

    /**
     * 忘记密码页面
     * @return
     */
    @RequestMapping("forget")
    public String forget() {
        return "background/user/forget";
    }


}
