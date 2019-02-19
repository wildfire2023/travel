package com.waner.primary.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>后台页面请求控制器</p>
 * @author Monster
 * @date 2019/1/10 16:42
 * @since 1.8
 */
@Controller
@RequestMapping("background/page")
public class BackGroundPageController {

    /**
     * 跳转后台控制台页面
     * @return
     */
    @GetMapping("index")
    public String toIndexPage() {
        return "background/index";
    }

    /**
     * 跳转到控制页面
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




}
