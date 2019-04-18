package com.waner.primary.web.controller;

import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.SysUser;
import com.waner.primary.web.entity.TravelQuestion;
import com.waner.primary.web.service.QuestionResolverService;
import com.waner.primary.web.vo.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 用户提问相关内容控制器
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Controller
@RequestMapping("question")
public class QuestionResolverController {
    private final QuestionResolverService questionResolverService;

    public QuestionResolverController(QuestionResolverService questionResolverService) {
        this.questionResolverService = questionResolverService;
    }

    /**
     * 定向到问题表单
     * @return
     */
    @GetMapping("form-page")
    public String redirectToQuestionFormPage() {
        return "front/question-form";
    }

    @PostMapping("add")
    @ResponseBody
    public Response<String> addQuestion(TravelQuestion question, HttpSession session) {
        if (ObjectUtils.isEmpty(question)) {
            throw new GlobalException("空参数", 500100);
        }
        SessionUser sysUser = (SessionUser) session.getAttribute("sessionUser");
        question.setSysUserId(sysUser.getId());
        int ret = questionResolverService.addQuestion(question);
        if (ret > 0) {
            return Response.success("提问成功");
        }else {
            return Response.fail(CodeMsg.FAIL);
        }
    }


}
