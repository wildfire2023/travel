package com.waner.primary;

import com.waner.primary.config.MailConfigurationProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * <p>程序启动类</p>
 *
 * @author Monster
 * @date 2018/12/22 17:05
 * @since 1.8
 */
@EnableConfigurationProperties(value = {MailConfigurationProperties.class})
@SpringBootApplication
@MapperScan("com.waner.primary.web.mapper")
public class PrimaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrimaryApplication.class, args);
    }


}

