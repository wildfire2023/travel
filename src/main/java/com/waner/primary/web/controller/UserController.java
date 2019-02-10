package com.waner.primary.web.controller;

import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.SysUser;
import com.waner.primary.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * <p>用户控制器</p>
 *
 * @author Monster
 * @date 2018/12/21 13:03
 * @since 1.8
 */
@Controller
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param sysUser 表单输入用户
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public Response<Boolean> login(SysUser sysUser, HttpSession session) {
        if (sysUser == null) {
            return Response.fail(CodeMsg.USER_NULL);
        }
        return userService.passwordLogin(sysUser, session);
    }

    /**
     * 用户注册url
     * @param sysUser
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    public Response<Boolean> register(SysUser sysUser) {

        return userService.register(sysUser);
    }

    /**
     * 发送邮箱随机数
     * @return
     */
    @PostMapping("emailSend")
    @ResponseBody
    public Response<Boolean> emailSend(String email){
        return  userService.sendVercode(email);
    }

}
