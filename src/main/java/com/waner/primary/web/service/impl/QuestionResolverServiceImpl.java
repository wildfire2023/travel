package com.waner.primary.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.TravelAnswer;
import com.waner.primary.web.entity.TravelEssay;
import com.waner.primary.web.entity.TravelQuestion;
import com.waner.primary.web.mapper.TravelAnswerMapper;
import com.waner.primary.web.mapper.TravelQuestionMapper;
import com.waner.primary.web.service.QuestionResolverService;
import com.waner.primary.web.vo.AnswerWithUser;
import com.waner.primary.web.vo.QuestionWithUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public QuestionResolverServiceImpl(TravelQuestionMapper questionMapper, TravelAnswerMapper answerMapper) {
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
    public Response<QuestionWithUser> getQuestionDetail(Integer id) {
        QuestionWithUser questionWithUser = questionMapper.selectQuestionWithUser(id);
        if (questionWithUser == null) {
            return Response.fail(CodeMsg.FAIL);
        }
        return Response.success(questionWithUser);
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
        answer.setSysUserId(userId).setContent(content)
                .setCreateTime(new Date()).setDelFlag((byte) 0);
        int answerRet = answerMapper.insertTravelAnswerMapper(answer);
        int questionAnswerRet = questionMapper.insertQuestionAnswer(questionId,answer.getId());
        return answerRet + questionAnswerRet;
    }

    /**
     * 根据给定questionId获取答案列表
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

        IPage<AnswerWithUser> answerWithUserIPage =  answerMapper.queryAnswerWithUser(pageHelper, questionId);
        return answerWithUserIPage.getRecords();
    }

    /**
     * 根据给定questionId获取答案数量
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
}
