package com.waner.primary.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.waner.primary.common.cache.UserKey;
import com.waner.primary.common.message.MailMessage;
import com.waner.primary.common.message.MessageProducer;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.common.util.CodeUtil;
import com.waner.primary.common.util.RedisUtil;
import com.waner.primary.web.entity.SysRole;
import com.waner.primary.web.entity.SysUser;
import com.waner.primary.web.entity.TravelUser;
import com.waner.primary.web.mapper.SysRoleMapper;
import com.waner.primary.web.mapper.SysUserMapper;
import com.waner.primary.web.mapper.TravelUserMapper;
import com.waner.primary.web.service.UserService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import javax.jms.Destination;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

/**
 * 用户服务实现类,提供用户服务接口实现
 *
 * <p>该类提供用户登录、注册、密码重置等基本服务
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Service
public class UserServiceImpl implements UserService {

    private final SysUserMapper sysUserMapper;
    private final TravelUserMapper travelUserMapper;
    private final MessageProducer messageProducer;
    private final RedisUtil redisUtil;
    private final SysRoleMapper sysRoleMapper;


    public UserServiceImpl(SysUserMapper sysUserMapper,
                           TravelUserMapper travelUserMapper,
                           MessageProducer messageProducer, RedisUtil redisUtil, SysRoleMapper sysRoleMapper) {
        this.sysUserMapper = sysUserMapper;
        this.travelUserMapper = travelUserMapper;
        this.messageProducer = messageProducer;
        this.redisUtil = redisUtil;
        this.sysRoleMapper = sysRoleMapper;
    }


    /**
     * 密码登录
     * 用户信息存储在session中
     *
     * @param sysUser
     */
    @Override
    @Transactional
    public Response<SysRole> passwordLogin(SysUser sysUser, HttpSession session) {
        // 根据用户邮箱进行查询
        String email = sysUser.getEmail();
        String md5FormPass = CodeUtil.md5(sysUser.getPassword());
        QueryWrapper<SysUser> emailQueryWrapper = new QueryWrapper<>();
        emailQueryWrapper.eq("email", email);
        emailQueryWrapper.eq("password", md5FormPass);
        SysUser dbUser = sysUserMapper.selectOne(emailQueryWrapper);
        // 判别数据库中md5密码与表单用户密码是否一致
        if (Objects.isNull(dbUser)) {
            return Response.fail(CodeMsg.USER_PASS_ERROR);
        }
        // 添加用户信息
        session.setAttribute("sessionUser", dbUser);
        // 设定session存储时长
        session.setMaxInactiveInterval(60 * 60 * 24);
        // 查询用户角色
        SysRole sysRole = sysRoleMapper.queryRoleByUserId(dbUser.getId());
        SysUser updateUser = new SysUser();
        updateUser.setId(dbUser.getId());
        updateUser.setOperatorTime(new Date());
        // 更新用户操作时间
        sysUserMapper.updateByPrimaryKeySelective(updateUser);
        return Response.success(sysRole);
    }

    /**
     * 注册用户
     *
     * @param sysUser
     * @param vercode
     * @return
     */
    @Override
    @Transactional
    public Response<Boolean> register(SysUser sysUser, String vercode) {
        String cacheCode = redisUtil.get(UserKey.MAIL_KEY, "register");
        // 缓存验证码为空
        if (StringUtils.isEmpty(cacheCode)) {
            return Response.fail(CodeMsg.CODE_TIME_OUT);
        }
        // 验证码错误
        if (!cacheCode.equals(vercode)) {
            return Response.fail(CodeMsg.VERCODE_ERROR);
        }
        // 查询数据库当前用户是否存在
        QueryWrapper<SysUser> emailWrapper = new QueryWrapper<>();
        emailWrapper.eq("email", sysUser.getEmail());
        SysUser dbEmailSysUser = sysUserMapper.selectOne(emailWrapper);
        // 对应email用户在数据库中存在
        if (!Objects.isNull(dbEmailSysUser)) {
            return Response.fail(CodeMsg.USER_EXIST_EMAIL);
        }
        QueryWrapper<SysUser> nicknameWrapper = new QueryWrapper<>();
        nicknameWrapper.eq("nickname", sysUser.getNickname());
        SysUser dbNicknameWrapper = sysUserMapper.selectOne(nicknameWrapper);
        if (!Objects.isNull(dbNicknameWrapper)) {
            return Response.fail(CodeMsg.USER_EXIST_NICKNAME);
        }
        // 设置数据库密码
        String dbPass = CodeUtil.md5(sysUser.getPassword());
        sysUser.setPassword(dbPass);
        boolean insertResult = sysUserMapper.insertSelective(sysUser) > 0;
        // 插入travel_user表格
        TravelUser travelUser = new TravelUser();
        travelUser.setNickname(sysUser.getNickname());
        travelUser.setSysUserId(sysUser.getId());
        int travelUserRet = travelUserMapper.insertSelective(travelUser);
        // 插入用户对应角色 TODO 硬编码角色为用户
        int sysUserRoleRet = sysRoleMapper.insertSysUserRole(sysUser.getId(), 2);

        if (!insertResult) {
            return Response.fail(CodeMsg.USER_REGISTER_ERROR);
        }
        return Response.success(true);
    }

    /**
     * 调用邮件发送服务
     * update 通用sendVercode
     *
     * @param email
     * @return
     */
    @Override
    @Transactional
    public Response<Boolean> sendVercode(String email, String mode) {
        // 邮件主题设置
        String subject = "玩儿旅游推荐网站";
        String verCode = CodeUtil.randomCode();
        // 邮件内容设置
        String content = "随机验证码：" + verCode + "\n"
                + "请在60秒内完整验证";

        MailMessage mailMessage = new MailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setContent(content);

        // 目标队列
        Destination destination = new ActiveMQQueue("mail-queue");
        messageProducer.sendMessage(destination, mailMessage);
        // 缓存vercode至redis
        return redisUtil.set(UserKey.MAIL_KEY, mode, verCode) ? Response.success(true) : Response.fail(CodeMsg.SERVER_ERROR);
    }


    /**
     * 重置密码
     *
     * @param sysUser
     * @param vercode
     * @return
     */
    @Override
    @Transactional
    public Response<Boolean> resetPass(SysUser sysUser, String vercode) {
        String email = sysUser.getEmail();
        String dbPass = CodeUtil.md5(sysUser.getPassword());
        // Query cache
        String cache = redisUtil.get(UserKey.MAIL_KEY, "forget");
        if (!cache.equals(vercode)) {
            return Response.fail(CodeMsg.VERCODE_ERROR);
        }
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        SysUser dbUser = sysUserMapper.selectOne(wrapper);
        if (dbUser == null) {
            return Response.fail(CodeMsg.USER_NULL);
        }
        dbUser.setPassword(dbPass);
        return sysUserMapper.updateByPrimaryKeySelective(dbUser) > 0 ? Response.success(true) : Response.fail(CodeMsg.SERVER_ERROR);
    }

    /**
     * 查询用户信息，供其他进行调用
     *
     * @param model
     * @param session
     */
    @Override
    @Transactional
    public void queryBaseInfo(Model model, HttpSession session) {
        SysUser sessionUser = (SysUser) session.getAttribute("sessionUser");
        Integer userId = sessionUser.getId();
        SysRole sysRole = sysRoleMapper.queryRoleByUserId(userId);
        if (!Objects.isNull(sysRole)) {
            model.addAttribute("roleInfo", sysRole);
        }
        TravelUser userInfo = travelUserMapper.selectByPrimaryKey(sessionUser.getId());
        if (!Objects.isNull(userInfo)) {
            model.addAttribute("userInfo", userInfo);
        }
    }

    /**
     * 图片地址存储
     * @param imgUrl
     * @param session
     */
    @Override
    public void saveImg(String imgUrl, HttpSession session) {
        SysUser sessionUser = (SysUser) session.getAttribute("sessionUser");
        Integer userId = sessionUser.getId();
        // 设置待更新用户信息
        TravelUser travelUser = new TravelUser();
        travelUser.setSysUserId(userId);
        travelUser.setImgUrl(imgUrl);
        // 更新TravelUser
        int ret = travelUserMapper.updateByPrimaryKeySelective(travelUser);
    }

    /**
     * 更改用户信息
     * @param nickname
     * @param sex
     * @param phone
     * @param remark
     * @param session
     * @return
     */
    @Override
    @Transactional
    public boolean modifyUserInfo(String nickname, Integer sex, String phone, String remark, HttpSession session) {
        // SysUser set
        SysUser sessionUser = (SysUser) session.getAttribute("sessionUser");
        SysUser sysUser = new SysUser();
        sysUser.setId(sessionUser.getId());
        sysUser.setNickname(nickname);
        sysUser.setPhone(phone);
        sysUser.setRemark(remark);
        // TravelUser set
        TravelUser travelUser = new TravelUser();
        travelUser.setSysUserId(sessionUser.getId());
        travelUser.setNickname(nickname);
        travelUser.setSex(sex.byteValue());
        int sysRet = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        // Reset session
        SysUser dbSessionUser = sysUserMapper.selectByPrimaryKey(sessionUser.getId());
        session.setAttribute("sessionUser", dbSessionUser);
        int travelRet = travelUserMapper.updateByPrimaryKeySelective(travelUser);
        return sysRet > 0 && travelRet > 0;
    }


}
