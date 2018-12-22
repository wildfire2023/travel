package com.waner.primary.web.mapper;

import com.waner.primary.web.entity.TravelUser;

public interface TravelUserMapper {
    int deleteByPrimaryKey(Integer sysUserId);

    int insert(TravelUser record);

    int insertSelective(TravelUser record);

    TravelUser selectByPrimaryKey(Integer sysUserId);

    int updateByPrimaryKeySelective(TravelUser record);

    int updateByPrimaryKey(TravelUser record);
}