package com.waner.primary.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootConfiguration
public class WebConfig extends WebMvcConfigurerAdapter {

    private final GlobalInterceptor globalInterceptor;

    public WebConfig(GlobalInterceptor globalInterceptor) {
        this.globalInterceptor = globalInterceptor;
    }

    /**
     * 拦截器注册
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor);
    }
}
