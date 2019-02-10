package com.waner.primary.common.message;

import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.web.service.impl.MailService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

/**
 * 消息消费
 *
 * @author Monster
 * @date 2019/2/10 21:30
 * @since 1.8
 */
@Component
public class MessageConsumer {

    private final MailService mailService;

    public MessageConsumer(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * 消息消费者
     * @param mailMessage
     */
    @JmsListener(destination = "mail-queue")
    public void receiveQueue(MailMessage mailMessage) {
        try {
            mailService.sendAttachmentsMail(mailMessage.getTo(), mailMessage.getSubject(), mailMessage.getContent(), "");
        } catch (MessagingException e) {
            throw new GlobalException(CodeMsg.MAIL_SEND_ERROR);
        }
    }
}
