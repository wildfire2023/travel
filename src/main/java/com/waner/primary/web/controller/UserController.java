package com.waner.primary.web.controller;

import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>用户控制器</p>
 * @author Monster
 * @date 2018/12/21 13:03
 * @since 1.8
 */
@Controller
@RequestMapping("user")
public class UserController {

    @GetMapping("test")
    public String test() {
        return "background/index";
    }


    /**
     *
     * @param sysUser 表单输入用户
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public Response<Boolean> login(SysUser sysUser) {
        return Response.fail(CodeMsg.SERVER_ERROR);
    }

}
