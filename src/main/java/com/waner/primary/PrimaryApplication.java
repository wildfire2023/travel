package com.waner.primary;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>程序启动类</p>
 * @author Monster
 * @date 2018/12/22 17:05
 * @since 1.8
 */
@SpringBootApplication
@MapperScan("com.wanner.primary.web.mapper")
public class PrimaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrimaryApplication.class, args);
    }

}

