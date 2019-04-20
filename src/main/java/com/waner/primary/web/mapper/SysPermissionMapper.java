package com.waner.primary.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waner.primary.web.entity.SysPermission;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    int deleteByPrimaryKey(Integer id);

    int insertSysPermissionMapper(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
}
