package com.waner.primary.config;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 邮件服务提供
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@SpringBootConfiguration
@ConfigurationProperties(prefix = "spring.mail")
@Data
public class MailConfigurationProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;

    /**
     *
     * @return JavaMailSender 作为Java邮件发送服务提供者
     */
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        return mailSender;
    }


}
