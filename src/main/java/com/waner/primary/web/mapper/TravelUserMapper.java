package com.waner.primary.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waner.primary.web.entity.TravelUser;

public interface TravelUserMapper extends BaseMapper<TravelUser> {
    int deleteByPrimaryKey(Integer sysUserId);

    int insertTravelUserMapper(TravelUser record);

    int insertSelective(TravelUser record);

    TravelUser selectByPrimaryKey(Integer sysUserId);

    int updateByPrimaryKeySelective(TravelUser record);

    int updateByPrimaryKey(TravelUser record);
}