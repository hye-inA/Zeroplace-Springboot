package com.demo.zeroplace.config.jwt;

// JWT 관련 설정

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
public class JwtProperties {
    private String secretKey = "";
    private long accessTokenValidityInSeconds = 300; // 5분
    private long refreshTokenValidityInSeconds = 86400; // 24시간

}
