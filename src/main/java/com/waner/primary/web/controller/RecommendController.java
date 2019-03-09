package com.waner.primary.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 推荐内容控制器
 * @author Monster
 * @date 2019/2/21 16:32
 * @since 1.8
 */
@Controller
@RequestMapping("recommend")
public class RecommendController {


    /**
     * 推荐内容列表
     * @return
     */
    @GetMapping("list")
    public String toRecommendList(@RequestParam(value = "role", required = false) String role){

        return "background/app/content/list";
    }

}
