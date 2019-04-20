package com.waner.primary.web.controller;

import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.TravelQuestion;
import com.waner.primary.web.service.QuestionResolverService;
import com.waner.primary.web.vo.QuestionWithUser;
import com.waner.primary.web.vo.SessionUser;
import com.waner.primary.web.vo.TableResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户提问相关控制器
 *
 * @author Monster
 * @since
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
     *
     * @return
     */
    @GetMapping("form-page")
    public String redirectToFormPage() {
        return "front/question-form";
    }

    /**
     * 定向到问题列表
     */
    @GetMapping("list-page")
    public String redirectToList() {
        return "front/question-list";
    }

    /**
     * 定向到问题详情页面
     */
    @GetMapping("detail-page")
    public String redirectToDetailPage(@RequestParam("id") Integer id, HttpServletRequest request) {
        request.setAttribute("id",id);
        return "question-detail";
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
        } else {
            return Response.fail(CodeMsg.FAIL);
        }
    }



    /**
     * 问答栏目列表
     *
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("list")
    @ResponseBody
    public TableResult<List<QuestionWithUser>> questionList(int page, int limit) {
        List<QuestionWithUser> questions = questionResolverService.listAll(page, limit);
        int count = questionResolverService.countAll();
        return new TableResult<>(200, "", count, questions);
    }

    @GetMapping("detail")
    @ResponseBody
    public Response<QuestionWithUser> getQuestionDetail(@RequestParam("id") Integer id) {
        return questionResolverService.getQuestionDetail(id);
    }
}
