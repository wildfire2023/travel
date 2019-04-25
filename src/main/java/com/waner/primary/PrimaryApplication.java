package com.waner.primary;

import com.waner.primary.config.MailConfigurationProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 程序启动类<
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@EnableConfigurationProperties(value = {MailConfigurationProperties.class})
@EnableJms
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.waner.primary.web.mapper")
public class PrimaryApplication {

  public static void main(String[] args) {
    SpringApplication.run(PrimaryApplication.class, args);
  }
}
