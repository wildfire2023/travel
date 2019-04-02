package com.waner.primary.web.controller;

import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.SysRole;
import com.waner.primary.web.entity.SysUser;
import com.waner.primary.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 用户控制器
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Controller
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * 到前台用户个人信息页面
     * @return
     */
    @GetMapping("person-info")
    public String toPersonInfoPage() {
        return "front/persinfo";
    }

    /**
     * @param sysUser 表单输入用户
     * @return 用户角色
     */
    @PostMapping("login")
    @ResponseBody
    public Response<SysRole> login(SysUser sysUser, HttpSession session) {
        if (sysUser == null) {
            return Response.fail(CodeMsg.USER_NULL);
        }
        return userService.passwordLogin(sysUser, session);
    }

    /**
     * 用户登出
     *
     * @return
     */
    @PostMapping("logout")
    @ResponseBody
    public Response<Boolean> logout(HttpSession session) {
        if (session != null) {
            session.removeAttribute("sessionUser");
            session.invalidate();
        }
        return Response.success(true);
    }

    /**
     * 用户注册url
     *
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
     *
     * @return
     */
    @PostMapping("email-send")
    @ResponseBody
    public Response<Boolean> emailSend(String email, String mode) {
        // 邮箱为空
        if (StringUtils.isEmpty(email)) {
            return Response.fail(CodeMsg.MAIL_NULL);
        }
        return userService.sendVercode(email, mode);
    }

    /**
     * 密码重置 ---> According to SysUser and vercode
     *
     * @param sysUser
     * @return
     */
    @PostMapping("pass-reset")
    @ResponseBody
    public Response<Boolean> resetPass(SysUser sysUser, String vercode) {
        if (StringUtils.isEmpty(sysUser.getEmail()) || StringUtils.isEmpty(sysUser.getPassword())) {
            throw new GlobalException("空参数", 501000);
        }
        return userService.resetPass(sysUser, vercode);
    }

    /**
     * 用户信息
     *
     * @param model
     * @param session
     * @return
     */
    @GetMapping("base-info")
    public String baseInfo(Model model, HttpSession session) {
        userService.queryBaseInfo(model, session);
        return "background/set/user/info";
    }


    /**
     * 修改用户信息
     *
     * @return
     */
    @PutMapping("modify-info")
    @ResponseBody
    public Response<Boolean> modifyInfo(String nickname, Integer sex,
                                        String phone, String remark, HttpSession session) {

        boolean ret = userService.modifyUserInfo(nickname, sex, phone, remark, session);
        if (!ret) {
            return Response.fail(CodeMsg.SERVER_ERROR);
        }
        return Response.success(true);
    }

}
