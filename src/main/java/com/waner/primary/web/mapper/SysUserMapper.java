package com.waner.primary.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waner.primary.web.entity.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser> {
    int deleteByPrimaryKey(Integer id);

    int insertSysUserMapper(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

}