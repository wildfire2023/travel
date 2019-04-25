package com.waner.primary.web.service;

import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.TravelQuestion;
import com.waner.primary.web.vo.AnswerWithUser;
import com.waner.primary.web.vo.QuestionWithUser;
import com.waner.primary.web.vo.Reply;
import com.waner.primary.web.vo.ResponseWithTag;

import java.util.List;

public interface QuestionResolverService {

  int addQuestion(TravelQuestion question);

  List<QuestionWithUser> listAll(int page, int limit);

  int countAll();

  Response<QuestionWithUser> getQuestionDetail(Integer id);

  int addAnswer(Integer questionId, Integer userId, String content);

  List<AnswerWithUser> getAnswers(Integer questionId, int page, int limit);

  int countAnswers(Integer questionId);

  List<TravelQuestion> queryQuestionByUser(Integer userId);

  List<ResponseWithTag> queryAnswersByUser(Integer userId);

  List<AnswerWithUser> queryReplysAccoring2Condition(Reply reply);

  void removeAnswer(Integer id);

  List<TravelQuestion> getListWithSearchPattern(String pattern);
}
