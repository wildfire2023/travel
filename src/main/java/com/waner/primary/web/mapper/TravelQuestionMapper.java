package com.waner.primary.web.mapper;

import com.waner.primary.web.entity.TravelQuestion;

public interface TravelQuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TravelQuestion record);

    int insertSelective(TravelQuestion record);

    TravelQuestion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TravelQuestion record);

    int updateByPrimaryKeyWithBLOBs(TravelQuestion record);

    int updateByPrimaryKey(TravelQuestion record);
}