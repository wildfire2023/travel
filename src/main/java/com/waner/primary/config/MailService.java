package com.waner.primary.config;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 邮件服务提供
 * @author Monster
 * @date 2019/2/2 22:29
 * @since 1.8
 */
@SpringBootConfiguration
@ConfigurationProperties(prefix = "spring.boot")
@Data
public class MailService {
    private String host;
    private Integer port;
    private String username;
    private String password;

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        return mailSender;
    }


}
