package com.waner.primary.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.TravelQuestion;
import com.waner.primary.web.mapper.TravelQuestionMapper;
import com.waner.primary.web.service.QuestionResolverService;
import com.waner.primary.web.vo.QuestionWithUser;
import org.springframework.stereotype.Service;

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

    public QuestionResolverServiceImpl(TravelQuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
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
}
