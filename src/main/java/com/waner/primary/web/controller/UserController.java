package com.waner.primary.web.controller;

import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.SysUser;
import com.waner.primary.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.chrono.IsoChronology;
import java.util.Optional;

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
     * 用户登出
     * @return
     */
    @PostMapping("logout")
    @ResponseBody
    public Response<Boolean> logout(HttpSession session) {
        if (session != null) {
            session.removeAttribute("userInfo");
            session.invalidate();
        }
        return Response.success(true);
    }

    /**
     * 用户注册url
     * @param sysUser
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    public Response<Boolean> register(SysUser sysUser, String vercode) {
        if (sysUser == null) {
            return Response.fail(CodeMsg.USER_NULL);
        }
        return userService.register(sysUser, vercode);
    }

    /**
     * 发送邮箱随机数
     * @return
     */
    @PostMapping("email-send")
    @ResponseBody
    public Response<Boolean> emailSend(String email, String mode){
        // 邮箱为空
        if (StringUtils.isEmpty(email)) {
            return Response.fail(CodeMsg.MAIL_NULL);
        }
        return  userService.sendVercode(email, mode);
    }

    /**
     * 密码重置
     * @param sysUser
     * @return
     */
    @PostMapping("pass-reset")
    @ResponseBody
    public Response<Boolean> resetPass(SysUser sysUser,String vercode) {
        if (StringUtils.isEmpty(sysUser.getEmail()) || StringUtils.isEmpty(sysUser.getPassword())) {
            throw new GlobalException("空参数", 501000);
        }
        return userService.resetPass(sysUser, vercode);
    }

}
