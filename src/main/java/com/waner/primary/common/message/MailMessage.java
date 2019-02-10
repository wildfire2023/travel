package com.waner.primary.common.message;

import lombok.Data;

import java.io.Serializable;

/**
 * 邮件消息对象
 * @author Monster
 * @date 2019/2/10 21:23
 * @since 1.8
 */
@Data
public class MailMessage implements Serializable {
    private static final long serialVersionUID = 4166120963605878421L;
    private String subject;
    private String content;
    private String to;
}
