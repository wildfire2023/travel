package com.waner.primary.web.controller;

import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.TravelRecommend;
import com.waner.primary.web.service.RecommendService;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 推荐内容控制器
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Controller
@RequestMapping("recommend")
public class RecommendController {

    private final RecommendService recommendService;

    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }


    /**
     * 推荐内容列表
     * @return
     */
    @GetMapping("list")
    public String toRecommendList(@RequestParam(value = "role", required = false) String role){

        return "background/app/content/list";
    }


    @GetMapping("add-page")
    public String recommendAddPage() {
        return "background/app/content/listform";
    }

    /**
     * 发表推荐内容
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public Response<String> addRecommend(@RequestBody TravelRecommend recommend) {
        if (ObjectUtils.isEmpty(recommend)) {
            throw new GlobalException("空参数", 500100);
        }
        int ret = recommendService.addRecommend(recommend);
        if (ret > 0) {
            return Response.success("添加成功");
        }else {
            return Response.fail(CodeMsg.FAIL);
        }
    }
}
