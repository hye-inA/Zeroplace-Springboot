package com.demo.zeroplace.service;

import com.demo.zeroplace.domain.Member;
import com.demo.zeroplace.domain.Session;
import com.demo.zeroplace.dto.request.Login;
import com.demo.zeroplace.exception.InValidSigninInformation;
import com.demo.zeroplace.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    @Transactional
    public String signin(Login login ){
        Member member = memberRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(()-> new InValidSigninInformation());
        Session session = member.addSession();

        return session.getAccessToken();
    }
}
