package com.waner.primary.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.dto.TopMap;
import com.waner.primary.web.entity.TravelAnswer;
import com.waner.primary.web.entity.TravelEssay;
import com.waner.primary.web.entity.TravelQuestion;
import com.waner.primary.web.mapper.TravelAnswerMapper;
import com.waner.primary.web.mapper.TravelQuestionMapper;
import com.waner.primary.web.service.QuestionResolverService;
import com.waner.primary.web.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 问题相关接口实现
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Service
public class QuestionResolverServiceImpl implements QuestionResolverService {
  private final TravelQuestionMapper questionMapper;
  private final TravelAnswerMapper answerMapper;

  public QuestionResolverServiceImpl(
      TravelQuestionMapper questionMapper, TravelAnswerMapper answerMapper) {
    this.questionMapper = questionMapper;
    this.answerMapper = answerMapper;
  }

  @Override
  public int addQuestion(TravelQuestion question) {
    return questionMapper.insert(question);
  }

  @Override
  public List<QuestionWithUser> listAll(int page, int limit) {
    Page<QuestionWithUser> pageHelper = new Page<>();
    pageHelper.setSize(limit);
    pageHelper.setCurrent(page);

    IPage<QuestionWithUser> pageVo;

    pageVo = questionMapper.queryAllQuestions(pageHelper);
    return pageVo.getRecords();
  }

  @Override
  public int countAll() {
    QueryWrapper<TravelQuestion> wrapper = new QueryWrapper<>();
    return questionMapper.selectCount(wrapper);
  }

  /**
   * 查找问题详情
   *
   * @param id
   * @return
   */
  @Override
  public QuestionWithUser getQuestionDetail(Integer id) {
    QuestionWithUser questionWithUser = questionMapper.selectQuestionWithUser(id);

    return questionWithUser;
  }

  /**
   * 添加问题答案
   *
   * @param questionId
   * @param userId
   * @param content
   * @return
   */
  @Transactional
  @Override
  public int addAnswer(Integer questionId, Integer userId, String content) {
    TravelAnswer answer = new TravelAnswer();
    answer.setSysUserId(userId).setContent(content).setCreateTime(new Date()).setDelFlag((byte) 0);
    int answerRet = answerMapper.insertTravelAnswerMapper(answer);
    int questionAnswerRet = questionMapper.insertQuestionAnswer(questionId, answer.getId());
    return answerRet + questionAnswerRet;
  }

  /**
   * 根据给定questionId获取答案列表
   *
   * @param questionId
   * @param page
   * @param limit
   * @return
   */
  @Override
  public List<AnswerWithUser> getAnswers(Integer questionId, int page, int limit) {
    Page<AnswerWithUser> pageHelper = new Page<>();
    pageHelper.setSize(limit);
    pageHelper.setCurrent(page);

    IPage<AnswerWithUser> answerWithUserIPage =
        answerMapper.queryAnswerWithUser(pageHelper, questionId);
    return answerWithUserIPage.getRecords();
  }

  /**
   * 根据给定questionId获取答案数量
   *
   * @param questionId
   * @return
   */
  @Override
  public int countAnswers(Integer questionId) {
    return answerMapper.queryAnswersWithUserCount(questionId);
  }

  @Override
  public List<TravelQuestion> queryQuestionByUser(Integer userId) {
    QueryWrapper<TravelQuestion> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("sys_user_id", userId);
    queryWrapper.eq("del_flag", (byte) 0);
    return questionMapper.selectList(queryWrapper);
  }

  /**
   * 根据用户编号查询所有的回答
   *
   * @param userId
   * @return
   */
  @Override
  public List<ResponseWithTag> queryAnswersByUser(Integer userId) {
    List<AnswerWithUser> answersWithUser = answerMapper.queryAnswersByUserId(userId);
    List<ResponseWithTag> results = Lists.newArrayList();
    answersWithUser.forEach(
        answerWithUser -> {
          ResponseWithTag result =
              ResponseWithTag.builder()
                  .tag("问答")
                  .responseContent(answerWithUser.getContent())
                  .responseCreateTime(answerWithUser.getCreateTime())
                  .userNickname(answerWithUser.getNickname())
                  .userHeadImgUrl(answerWithUser.getImgUrl())
                  .build();
          Integer answerId = answerWithUser.getId();
          int questionId = answerMapper.queryQuestionIdByAnswerId(answerId);
          int answersCount = answerMapper.queryAnswersWithUserCount(questionId);
          TravelQuestion travelQuestion = questionMapper.selectByPrimaryKey(questionId);
          result
              .setCommentsCount(answersCount)
              .setArticleId(travelQuestion.getId())
              .setArticleCreateTime(travelQuestion.getCreateTime())
              .setArticleTitle(travelQuestion.getTitle());
          results.add(result);
        });
    return results;
  }

  @Override
  public List<AnswerWithUser> queryReplysAccoring2Condition(Reply reply) {
    List<AnswerWithUser> result = null;
    if (reply == null) {
      result = answerMapper.queryWithContent(null);
    } else {
      result = answerMapper.queryWithContent(reply.getTitle());
    }
    return result;
  }

  @Override
  public void removeAnswer(Integer id) {
    answerMapper.deleteByPrimaryKey(id);
    answerMapper.deleteQuestionAnswerAccording2AnswerId(id);
  }

  @Override
  public List<TravelQuestion> getListWithSearchPattern(String pattern) {
    QueryWrapper<TravelQuestion> wrapper = new QueryWrapper<>();
    wrapper.like("title", pattern);
    return questionMapper.selectList(wrapper);
  }

  @Override
  public List<ArticleWithTag> getListByIds(List<Integer> questionIds) {
    if (questionIds == null || questionIds.size() == 0){
      return null;
    }
    QueryWrapper<TravelQuestion> wrapper= new QueryWrapper<>();
    wrapper.in("id",questionIds);
    List<TravelQuestion> recommends = questionMapper.selectList(wrapper);
    ArrayList<ArticleWithTag> results = Lists.newArrayList();
    recommends.forEach(
            question -> {
              ArticleWithTag articleWithTag =
                      ArticleWithTag.builder()
                              .id(question.getId())
                              .tag("问答")
                              .createTime(question.getCreateTime())
                              .delFLag(question.getDelFlag())
                              .title(question.getTitle())
                              .build();
              results.add(articleWithTag);
            });
    return results;
  }

  @Override
  public Response<List<TravelQuestion>> top(List<TopMap> questions) {
    if (questions == null || questions.size() == 0) {
      return Response.fail(null);
    }
    List<Integer> ids = Lists.newArrayList();
    questions.forEach(top -> ids.add(top.getArticleId()));
    List<TravelQuestion> questionList = new ArrayList<>();
    // 按照排名先后查询
    ids.forEach(
        id -> {
          // 查询已发布的游记
          TravelQuestion question = questionMapper.selectByPrimaryKey(id);
          if (question != null) {
            questionList.add(question);
          }
        });
    List<TravelQuestion> results = null;
    if (questionList.size() > 10) {
      results = questionList.subList(0, 10);
    }
    return results != null ? Response.success(results) : Response.success(questionList);
  }
}
