package com.demo.zeroplace.controller;

import com.demo.zeroplace.dto.request.Login;
import com.demo.zeroplace.dto.response.SessionResponse;
import com.demo.zeroplace.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {

        String accessToken = authService.signin(login);
        return new SessionResponse(accessToken);
    }
}
