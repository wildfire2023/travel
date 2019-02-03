package com.waner.primary.web.service;

import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.SysUser;

import javax.servlet.http.HttpSession;

public interface UserService {

    /**
     * login with user password
     * @param sysUser form user
     * @return REST api
     */
    Response<Boolean> passwordLogin(SysUser sysUser, HttpSession session);

    /**
     * register user info to db
     * @param sysUser
     * @return
     */
    Response<Boolean> register(SysUser sysUser);
}
