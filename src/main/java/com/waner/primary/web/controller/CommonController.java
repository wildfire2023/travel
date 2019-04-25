package com.waner.primary.web.controller;

import com.google.common.collect.Lists;
import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.web.entity.TravelEssay;
import com.waner.primary.web.entity.TravelQuestion;
import com.waner.primary.web.service.EssayCommentService;
import com.waner.primary.web.service.QuestionResolverService;
import com.waner.primary.web.service.TravelEssayService;
import com.waner.primary.web.vo.ArticleWithTag;
import com.waner.primary.web.vo.ResponseWithTag;
import com.waner.primary.web.vo.TableResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Common usage to list the user's pushed articles, also include common top viewed articles.
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Controller
@RequestMapping("common")
public class CommonController {
  private final TravelEssayService travelEssayService;
  private final QuestionResolverService questionResolverService;
  private final EssayCommentService essayCommentService;

  public CommonController(
      TravelEssayService travelEssayService,
      QuestionResolverService questionResolverService,
      EssayCommentService essayCommentService) {
    this.travelEssayService = travelEssayService;
    this.questionResolverService = questionResolverService;
    this.essayCommentService = essayCommentService;
  }

  /**
   * 获取撰写的内容列表
   *
   * @param userId
   * @param page
   * @param limit
   * @return
   */
  @GetMapping("articles")
  @ResponseBody
  public TableResult<List<ArticleWithTag>> getMyArticles(
      @RequestParam(value = "userId") Integer userId, int page, int limit) {
    if (userId == null) {
      throw new GlobalException("空参数", 500100);
    }
    List<ArticleWithTag> articlesWithTag = Lists.newArrayList();
    List<TravelEssay> essays = travelEssayService.queryEssayByUser(userId);
    List<TravelQuestion> questions = questionResolverService.queryQuestionByUser(userId);
    essays.forEach(
        article -> {
          ArticleWithTag articleWithTag = ArticleWithTag.builder().build();
          BeanUtils.copyProperties(article, articleWithTag);
          articleWithTag.setTag("essay");
          articlesWithTag.add(articleWithTag);
        });
    questions.forEach(
        article -> {
          ArticleWithTag articleWithTag = ArticleWithTag.builder().build();
          BeanUtils.copyProperties(article, articleWithTag);
          articleWithTag.setTag("question");
          articlesWithTag.add(articleWithTag);
        });
    articlesWithTag.sort((a1, a2) -> a2.getCreateTime().compareTo(a1.getCreateTime()));
    int start = (page - 1) * limit;
    int end = page * limit - 1;
    List<ArticleWithTag> result = Lists.newArrayList();
    for (int i = start; i <= end; i++) {
      if (i > articlesWithTag.size() - 1) {
        break;
      }
      result.add(articlesWithTag.get(i));
    }
    return new TableResult<>(200, "", articlesWithTag.size(), result);
  }

  /**
   * 获取回复内容列表
   *
   * @param userId
   * @param page
   * @param limit
   * @return
   */
  @GetMapping("responses")
  @ResponseBody
  public TableResult<List<ResponseWithTag>> getMyResponses(
      @RequestParam(value = "userId") Integer userId, int page, int limit) {
    if (userId == null) {
      throw new GlobalException("空参数", 500100);
    }
    List<ResponseWithTag> responsesWithTag = Lists.newArrayList();
    List<ResponseWithTag> answersWithTag = questionResolverService.queryAnswersByUser(userId);
    List<ResponseWithTag> replysWithTag = essayCommentService.queryReplysByUser(userId);
    responsesWithTag.addAll(answersWithTag);
    responsesWithTag.addAll(replysWithTag);
    List<ResponseWithTag> sortedList =
        responsesWithTag
            .parallelStream()
            .sorted((r1, r2) -> r2.getResponseCreateTime().compareTo(r1.getResponseCreateTime()))
            .collect(Collectors.toList());
    int start = (page - 1) * limit;
    int end = page * limit - 1;
    List<ResponseWithTag> results = Lists.newArrayList();
    for (int i = start; i <= end; i++) {
      if (i > sortedList.size() - 1) {
        break;
      }
      results.add(sortedList.get(i));
    }
    return new TableResult<>(200, "", sortedList.size(), results);
  }

  public TableResult<List<>>
}
