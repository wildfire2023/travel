package com.waner.primary.web.controller;

import com.waner.primary.common.cache.ViewKey;
import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.TravelQuestion;
import com.waner.primary.web.service.QuestionResolverService;
import com.waner.primary.web.service.impl.RedisService;
import com.waner.primary.web.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
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
  private final RedisService redisService;

  public QuestionResolverController(
      QuestionResolverService questionResolverService, RedisService redisService) {
    this.questionResolverService = questionResolverService;
    this.redisService = redisService;
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

  /** 定向到问题列表 */
  @GetMapping("list-page")
  public String redirectToList() {
    return "front/question-list";
  }

  /** 定向到问题详情页面 */
  @GetMapping("detail-page")
  public String redirectToDetailPage(@RequestParam("id") Integer id, HttpServletRequest request) {
    request.setAttribute("id", id);
    redisService.setViewNum(id, ViewKey.QUESTION_KEY);
    return "front/question-detail";
  }

  /**
   * 添加问题请求映射
   *
   * @param question
   * @param session
   * @return
   */
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
  public Response<QuestionWithUserAndView> getQuestionDetail(@RequestParam("id") Integer id) {
    QuestionWithUser questionDetail = questionResolverService.getQuestionDetail(id);
    QuestionWithUserAndView withUserAndView = new QuestionWithUserAndView();
    BeanUtils.copyProperties(questionDetail, withUserAndView);
    Number viewNum = redisService.getViewNum(id, ViewKey.QUESTION_KEY);
    withUserAndView.setViewNum(viewNum.longValue());
    return Response.success(withUserAndView);
  }

  @PostMapping("add-answer")
  @ResponseBody
  public Response<String> addAnswer(Integer questionId, Integer userId, String content) {
    if (questionId == null || userId == null || StringUtils.isEmpty(content)) {
      throw new GlobalException("空参数", 500100);
    }
    int ret = questionResolverService.addAnswer(questionId, userId, content);
    if (ret > 1) {
      return Response.success("评论成功");
    } else {
      return Response.fail(CodeMsg.FAIL);
    }
  }

  @PostMapping("list-answers")
  @ResponseBody
  public TableResult<List<AnswerWithUser>> getAnswers(Integer questionId, int page, int limit) {
    if (questionId == null) {
      throw new GlobalException("空参数", 500100);
    }
    List<AnswerWithUser> answers = questionResolverService.getAnswers(questionId, page, limit);
    int count = questionResolverService.countAnswers(questionId);
    return new TableResult<>(200, "", count, answers);
  }
}
