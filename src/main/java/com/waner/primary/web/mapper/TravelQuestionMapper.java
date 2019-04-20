package com.waner.primary.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.waner.primary.web.entity.TravelQuestion;
import com.waner.primary.web.vo.QuestionWithUser;

public interface TravelQuestionMapper extends BaseMapper<TravelQuestion> {
    int deleteByPrimaryKey(Integer id);

    int insertTravelQuetionMapper(TravelQuestion record);

    int insertSelective(TravelQuestion record);

    TravelQuestion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TravelQuestion record);

    int updateByPrimaryKeyWithBLOBs(TravelQuestion record);

    int updateByPrimaryKey(TravelQuestion record);

    IPage<QuestionWithUser> queryAllQuestions(Page pageVo);
}
