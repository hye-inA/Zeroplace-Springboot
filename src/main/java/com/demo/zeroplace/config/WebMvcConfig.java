package com.demo.zeroplace.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                //.excludePathPatterns("/exceptapi");

                // "/error" 스프링에서 Base Controller에서 기본적으로 뭔가 문제가 생겼을때 이동시켜주는 라우터 경로
                .excludePathPatterns("/error", "/favicon.ico");
    }
}
