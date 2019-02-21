package com.waner.primary.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waner.primary.web.entity.SysRole;
import org.apache.ibatis.annotations.Param;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    int deleteByPrimaryKey(Integer id);

    int insertSysRoleMapper(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    SysRole queryRoleByUserId(@Param("userId") Integer userId);
}