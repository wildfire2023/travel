package com.waner.primary.web.vo;

import com.waner.primary.web.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SessionUser extends SysUser {
    private String imgUrl;
}
