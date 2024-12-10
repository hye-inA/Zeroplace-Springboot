package com.demo.zeroplace.controller;


import com.demo.zeroplace.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    // 로그인 로직은 BasicAuthenticationFilter에서 처리되므로 제거

    // 토큰 갱신 엔드포인트 추가
    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        try {
            // Bearer 제거
            refreshToken = refreshToken.substring(7);
            // 토큰 갱신 로직
            String newAccessToken = authService.refreshAccessToken(refreshToken);

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid refresh token"));
        }
    }

    // 로그아웃 엔드포인트 추가
    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        // 로그아웃 처리 로직
        return ResponseEntity.ok().build();
    }
}