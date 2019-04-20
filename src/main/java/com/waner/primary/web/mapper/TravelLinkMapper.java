package com.waner.primary.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waner.primary.web.entity.TravelLink;

public interface TravelLinkMapper extends BaseMapper<TravelLink> {
    int deleteByPrimaryKey(Integer id);

    int insertTravelLinkMapper(TravelLink record);

    int insertSelective(TravelLink record);

    TravelLink selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TravelLink record);

    int updateByPrimaryKey(TravelLink record);
}
