package com.waner.primary.web.service.impl;

import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.common.util.CodeUtil;
import com.waner.primary.web.entity.SysUser;
import com.waner.primary.web.mapper.SysUserMapper;
import com.waner.primary.web.mapper.TravelUserMapper;
import com.waner.primary.web.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    private final SysUserMapper sysUserMapper;
    private final TravelUserMapper travelUserMapper;


    public UserServiceImpl(SysUserMapper sysUserMapper,
                           TravelUserMapper travelUserMapper) {
        this.sysUserMapper = sysUserMapper;
        this.travelUserMapper = travelUserMapper;
    }


    /**
     * 密码登录
     *
     * @param sysUser
     */
    @Override
    @Transactional
    public Response<Boolean> passwordLogin(SysUser sysUser, HttpSession session) {
        // 根据用户Id进行查询
        SysUser dbUser = sysUserMapper.selectById(sysUser.getId());

        String md5FormPass = CodeUtil.md5(sysUser.getPassword());
        // 判别数据库中md5密码与表单用户密码是否一致
        if (!dbUser.getPassword().equals(md5FormPass)) {
            throw new GlobalException(CodeMsg.PASS_ERROR);
        }
        // 添加用户信息
        session.setAttribute("userInfo", dbUser);
        // 设定session存储时长
        session.setMaxInactiveInterval(60 * 60 * 24);
        return Response.success(true);
    }

    /**
     * 注册用户
     * @param sysUser
     * @return
     */
    @Override
    public Response<Boolean> register(SysUser sysUser) {
        // 设置数据库密码
        String dbPass = CodeUtil.md5(sysUser.getPassword());
        sysUser.setPassword(dbPass);

        boolean insertResult = sysUserMapper.insert(sysUser) > 0;
        if (!insertResult) {
            throw new GlobalException(CodeMsg.USER_REGISTER_ERROR);
        }
        return Response.success(true);
    }


}
