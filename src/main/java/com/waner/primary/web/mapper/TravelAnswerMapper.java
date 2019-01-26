package com.waner.primary.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waner.primary.web.entity.TravelAnswer;

public interface TravelAnswerMapper extends BaseMapper<TravelAnswer> {
    int deleteByPrimaryKey(Integer id);

    int insertTravelAnswerMapper(TravelAnswer record);

    int insertSelective(TravelAnswer record);

    TravelAnswer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TravelAnswer record);

    int updateByPrimaryKeyWithBLOBs(TravelAnswer record);

    int updateByPrimaryKey(TravelAnswer record);
}