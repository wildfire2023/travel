package com.waner.primary.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waner.primary.web.entity.TravelEssay;

public interface TravelEssayMapper extends BaseMapper<TravelEssay> {
    int deleteByPrimaryKey(Integer id);

    int insert(TravelEssay record);

    int insertSelective(TravelEssay record);

    TravelEssay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TravelEssay record);

    int updateByPrimaryKeyWithBLOBs(TravelEssay record);

    int updateByPrimaryKey(TravelEssay record);
}