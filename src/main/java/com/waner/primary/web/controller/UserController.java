package com.waner.primary.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>用户控制器</p>
 * @author Monster
 * @date 2018/12/21 13:03
 * @since 1.8
 */
@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping("test")
    public String test() {
        return "background/index";
    }

}
