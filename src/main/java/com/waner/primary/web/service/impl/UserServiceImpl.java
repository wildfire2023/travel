package com.waner.primary.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.waner.primary.common.cache.UserKey;
import com.waner.primary.common.message.MailMessage;
import com.waner.primary.common.message.MessageProducer;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.common.util.CodeUtil;
import com.waner.primary.common.util.RedisUtil;
import com.waner.primary.web.entity.SysUser;
import com.waner.primary.web.mapper.SysUserMapper;
import com.waner.primary.web.mapper.TravelUserMapper;
import com.waner.primary.web.service.UserService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.jms.Destination;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * 用户服务实现类
 *
 * @author Monster
 * @date 2019/2/7 15:08
 * @since 1.8
 */
@Service
public class UserServiceImpl implements UserService {

    private final SysUserMapper sysUserMapper;
    private final TravelUserMapper travelUserMapper;
    private final MessageProducer messageProducer;
    private final RedisUtil redisUtil;


    public UserServiceImpl(SysUserMapper sysUserMapper,
                           TravelUserMapper travelUserMapper,
                           MessageProducer messageProducer, RedisUtil redisUtil) {
        this.sysUserMapper = sysUserMapper;
        this.travelUserMapper = travelUserMapper;
        this.messageProducer = messageProducer;
        this.redisUtil = redisUtil;
    }


    /**
     * 密码登录
     * TODO 用户信息存储
     * 用户信息存储在session中
     *
     * @param sysUser
     */
    @Override
    @Transactional
    public Response<Boolean> passwordLogin(SysUser sysUser, HttpSession session) {
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
        session.setAttribute("userInfo", dbUser);
        // 设定session存储时长
        session.setMaxInactiveInterval(60 * 60 * 24);
        return Response.success(true);
    }

    /**
     *
     * 注册用户
     *
     * @param sysUser
     * @param vercode
     * @return
     */
    @Override
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
        boolean insertResult = sysUserMapper.insert(sysUser) > 0;
        if (!insertResult) {
            return Response.fail(CodeMsg.USER_REGISTER_ERROR);
        }
        return Response.success(true);
    }

    /**
     * 调用邮件发送服务
     *
     * @param email
     * @return
     */
    @Override
    @Transactional
    public Response<Boolean> sendVercode(String email) {
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
        return  redisUtil.set(UserKey.MAIL_KEY, "register", verCode) ? Response.success(true) : Response.fail(CodeMsg.SERVER_ERROR);
    }


}
