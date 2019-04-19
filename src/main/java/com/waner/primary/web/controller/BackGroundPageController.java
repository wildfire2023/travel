package com.waner.primary.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台页面请求控制器
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Controller
@RequestMapping("background/page")
public class BackGroundPageController {

    /**
     * 跳转后台控制台页面
     *
     * @return
     */
    @GetMapping("index")
    public String toIndexPage() {
        return "background/index";
    }

    /**
     * 跳转到控制页面
     *
     * @return
     */
    @GetMapping("console")
    public String toConsolePage() {
        return "background/home/console";
    }

    @GetMapping("home1")
    public String toHome1Page() {
        return "background/home/homepage1";
    }

    /**
     * 弃用内部修改密码，转为实现登录界面实现邮箱密码修改
     *
     * @return
     */
    @Deprecated
    @GetMapping("reset-pass")
    public String resetPassPage() {
        return "background/set/user/password";
    }
}
