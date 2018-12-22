package com.waner.primary.web.mapper;

import com.waner.primary.web.entity.TravelAnswer;

public interface TravelAnswerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TravelAnswer record);

    int insertSelective(TravelAnswer record);

    TravelAnswer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TravelAnswer record);

    int updateByPrimaryKeyWithBLOBs(TravelAnswer record);

    int updateByPrimaryKey(TravelAnswer record);
}