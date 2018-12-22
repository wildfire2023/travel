package com.waner.primary.web.mapper;

import com.waner.primary.web.entity.TravelEssay;

public interface TravelEssayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TravelEssay record);

    int insertSelective(TravelEssay record);

    TravelEssay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TravelEssay record);

    int updateByPrimaryKeyWithBLOBs(TravelEssay record);

    int updateByPrimaryKey(TravelEssay record);
}