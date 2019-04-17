package com.waner.primary.web.service.impl;

import com.waner.primary.web.mapper.TravelQuestionMapper;
import com.waner.primary.web.service.QuestionResolverService;
import org.springframework.stereotype.Service;

@Service
public class QuestionResolverServiceImpl implements QuestionResolverService {
    private final TravelQuestionMapper questionMapper;

    public QuestionResolverServiceImpl(TravelQuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }


}
