package com.waner.primary.web.service;

import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.TravelQuestion;
import com.waner.primary.web.vo.QuestionWithUser;

import java.util.List;

public interface QuestionResolverService {

    int addQuestion(TravelQuestion question);

    List<QuestionWithUser> listAll(int page, int limit);

    int countAll();

    Response<QuestionWithUser> getQuestionDetail(Integer id);
}
