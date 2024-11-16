package com.demo.zeroplace.controller;

import com.demo.zeroplace.domain.Member;
import com.demo.zeroplace.dto.request.Login;
import com.demo.zeroplace.exception.InValidSigninInformation;
import com.demo.zeroplace.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberRepository memberRepository;
    @PostMapping("/auth/login")
    public Member login(@RequestBody Login login) {
        // json으로 아이디/비밀번호
        log.info(">>>login={}", login);

        // DB에서 조회
        Member member = memberRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(()-> new InValidSigninInformation());

        return member;

        // 토큰을 응답


    }
}
