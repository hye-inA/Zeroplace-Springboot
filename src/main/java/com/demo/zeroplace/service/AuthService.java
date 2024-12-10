package com.demo.zeroplace.service;

import com.demo.zeroplace.config.jwt.JwtUtil;
import com.demo.zeroplace.domain.Member;
import com.demo.zeroplace.exception.ExpiredTokenException;
import com.demo.zeroplace.exception.InvalidTokenException;
import com.demo.zeroplace.exception.MemberNotFound;
import com.demo.zeroplace.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    public String refreshAccessToken(String refreshToken) {
        try {
            // 리프레시 토큰 검증 및 사용자 ID 추출
            Long memberId = jwtUtil.getMemberIdFromToken(refreshToken);

            // 사용자 조회
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new MemberNotFound());

            // 새로운 액세스 토큰 발급
            return jwtUtil.generateAccessToken(member);
        } catch (ExpiredTokenException e) {
            throw new RuntimeException("만료된 리프레시 토큰입니다.");
        } catch (InvalidTokenException e) {
            throw new RuntimeException("유효하지 않은 리프레시 토큰입니다.");
        }
    }
}