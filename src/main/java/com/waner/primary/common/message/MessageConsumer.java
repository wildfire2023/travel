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
 * @since 1.0.0-SNAPSHOT
 */
@Component
public class MessageConsumer {

    private final MailService mailService;

    public MessageConsumer(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * 消息消费者
     *
     * @param mailMessage
     */
    @JmsListener(destination = "mail-queue")
    public void receiveQueue(MailMessage mailMessage) {
        try {
            mailService.sendAttachmentsMail(
                    mailMessage.getTo(), mailMessage.getSubject(), mailMessage.getContent(), "");
        } catch (MessagingException e) {
            throw new GlobalException(CodeMsg.SERVER_ERROR.getMsg(), CodeMsg.SERVER_ERROR.getCode());
        }
    }
}
