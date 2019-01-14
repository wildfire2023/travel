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

    @RequestMapping("console")
    public String toConsolePage() {
        return "background/home/console";
    }
}
