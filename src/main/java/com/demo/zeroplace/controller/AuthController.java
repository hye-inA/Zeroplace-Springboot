package com.demo.zeroplace.controller;

import com.demo.zeroplace.dto.request.Login;
import com.demo.zeroplace.dto.response.SessionResponse;
import com.demo.zeroplace.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Base64;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final String KEY = ;
    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        Long memberId = authService.signin(login);

        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(KEY));

        String jws = Jwts.builder()
                .subject(String.valueOf(memberId))
                .signWith(key)
                .compact();

        return new SessionResponse(jws);
    }
}
