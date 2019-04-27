package com.waner.primary.web.controller;

import com.google.common.collect.Lists;
import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.TravelEssay;
import com.waner.primary.web.entity.TravelQuestion;
import com.waner.primary.web.entity.TravelRecommend;
import com.waner.primary.web.service.EssayCommentService;
import com.waner.primary.web.service.QuestionResolverService;
import com.waner.primary.web.service.RecommendService;
import com.waner.primary.web.service.TravelEssayService;
import com.waner.primary.web.service.impl.RedisService;
import com.waner.primary.web.vo.ArticleWithTag;
import com.waner.primary.web.vo.ResponseWithTag;
import com.waner.primary.web.vo.SearchInfoWithTag;
import com.waner.primary.web.vo.TableResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
  private final RecommendService recommendService;
  private final TravelEssayService essayService;
  private final RedisService redisService;

  public CommonController(
      TravelEssayService travelEssayService,
      QuestionResolverService questionResolverService,
      EssayCommentService essayCommentService,
      RecommendService recommendService,
      TravelEssayService essayService,
      RedisService redisService) {
    this.travelEssayService = travelEssayService;
    this.questionResolverService = questionResolverService;
    this.essayCommentService = essayCommentService;
    this.recommendService = recommendService;
    this.essayService = essayService;
    this.redisService = redisService;
  }

  // -------------------------------------------------------------------------
  // 个人信息列表获取
  // -------------------------------------------------------------------------

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
   * 获取收藏列表
   * @param userId
   * @param page
   * @param limit
   * @return
   */
  @GetMapping("collections")
  @ResponseBody
  public TableResult<List<ArticleWithTag>> getCollections(
          @RequestParam(value = "userId") Integer userId, int page, int limit) {
    if (userId == null) {
      throw new GlobalException("空参数", 500100);
    }
    List<ArticleWithTag> articlesWithTag = Lists.newArrayList();
    Map<String, List<Integer>> withTagArticleIds = redisService.getCollectionArticlesId(userId);
    List<Integer> recommendIds = withTagArticleIds.get("recommend");
    List<Integer> essayIds = withTagArticleIds.get("essay");
    List<Integer> questionIds = withTagArticleIds.get("question");
    List<ArticleWithTag> recommends = recommendService.getListByIds(recommendIds);
    List<ArticleWithTag> essays = essayService.getListByIds(essayIds);
    List<ArticleWithTag> questions = questionResolverService.getListByIds(questionIds);
    if (recommends != null) {
      articlesWithTag.addAll(recommends);
    }
    if (essays != null) {
      articlesWithTag.addAll(essays);
    }
    if (questions != null) {
      articlesWithTag.addAll(questions);
    }
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

  /**
   * 根据搜索的内容进行匹配
   *
   * @param pattern 匹配模式
   * @return
   */
  @GetMapping("search-list")
  @ResponseBody
  public TableResult<List<SearchInfoWithTag>> getSearchList(String pattern, int page, int limit) {
    List<SearchInfoWithTag> searchs = Lists.newArrayList();
    List<TravelRecommend> recommends = recommendService.getListWithSearchPattern(pattern);
    List<TravelEssay> essays = essayService.getListWithSearchPattern(pattern);
    List<TravelQuestion> questions = questionResolverService.getListWithSearchPattern(pattern);
    recommends.forEach(
        recommend -> {
          SearchInfoWithTag searchInfo =
              SearchInfoWithTag.builder()
                  .id(recommend.getId())
                  .content(recommend.getContent())
                  .tag("推荐")
                  .title(recommend.getTitle())
                  .createTime(recommend.getCreateTime())
                  .build();
          searchs.add(searchInfo);
        });
    essays.forEach(
        essay -> {
          SearchInfoWithTag searchInfo =
              SearchInfoWithTag.builder()
                  .id(essay.getId())
                  .content(essay.getContent())
                  .tag("游记")
                  .title(essay.getTitle())
                  .createTime(essay.getCreateTime())
                  .build();
          searchs.add(searchInfo);
        });
    questions.forEach(
        question -> {
          SearchInfoWithTag searchInfo =
              SearchInfoWithTag.builder()
                  .id(question.getId())
                  .content(question.getContent())
                  .tag("问答")
                  .title(question.getTitle())
                  .createTime(question.getCreateTime())
                  .build();
          searchs.add(searchInfo);
        });
    searchs.sort((s1, s2) -> s2.getCreateTime().compareTo(s1.getCreateTime()));
    int start = (page - 1) * limit;
    int end = page * limit - 1;
    List<SearchInfoWithTag> results = Lists.newArrayList();
    for (int i = start; i <= end; i++) {
      if (i > searchs.size() - 1) {
        break;
      }
      results.add(searchs.get(i));
    }
    return new TableResult<>(200, "", searchs.size(), results);
  }

  // -------------------------------------------------------------------------
  // 文章收藏
  // -------------------------------------------------------------------------

  /**
   * 获取用户收藏状态
   *
   * @param type 文章所属类型
   * @param articleId 文章编号
   * @param userId 用户编号
   * @return
   */
  @GetMapping("collection-state")
  @ResponseBody
  public Response<Boolean> getCollectionState(String type, Integer articleId, Integer userId) {
    boolean state = redisService.getCollectionState(type, articleId, userId);
    return Response.success(state);
  }

  /**
   * 设置用户收藏
   *
   * @param type
   * @param articleId
   * @param userId
   */
  @PutMapping("collection-state-set")
  @ResponseBody
  public void setCollectionState(String type, Integer articleId, Integer userId) {
    redisService.setCollectionState(type, articleId, userId);
  }

  /**
   * 删除用户收藏
   *
   * @param type
   * @param articleId
   * @param userId
   */
  @PostMapping("collection-state-del")
  @ResponseBody
  public void delCollectionState(String type, Integer articleId, Integer userId) {
    redisService.delCollectionState(type, articleId, userId);
  }
}
