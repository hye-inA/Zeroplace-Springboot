package com.demo.zeroplace.config.filter;

import com.demo.zeroplace.config.jwt.JwtUtil;
import com.demo.zeroplace.domain.Member;
import com.demo.zeroplace.exception.MemberNotFound;
import com.demo.zeroplace.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

// Basic 인증 처리 필터
@Slf4j
@Component
@RequiredArgsConstructor
public class BasicAuthenticationFilter extends OncePerRequestFilter {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String path = request.getServletPath();

        log.info("Request path: {}", path);  // 요청 경로 로깅

        // /auth/login 요청만 처리
        if (!path.equals("/auth/login")) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        log.info("Authorization header: {}", header);  // 인증 헤더 로깅

        if (header == null || !header.startsWith("Basic ")) {
            log.error("Basic authentication header is missing or invalid");
            sendErrorResponse(response, "Basic 인증이 필요합니다.");
            return;
        }

        try {
            String[] credentials = decodeBasicAuth(header);
            log.info("Decoded email: {}", credentials[0]);  // 디코딩된 이메일 로깅
            String email = credentials[0];
            String password = credentials[1];

            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new MemberNotFound());

            if (member != null) {
                log.info("Member found: {}", member.getEmail());
            } else {
                log.info("No member found with email: {}", email);
            }

            // 비밀번호 체크 전에 로그 추가
            log.info("Password check - Input: {}, Stored: {}", password, member.getPassword());

            if (!password.equals(member.getPassword())) {
                sendErrorResponse(response, "비밀번호가 일치하지 않습니다.");
                return;
            }

            String accessToken = jwtUtil.generateAccessToken(member);
            String refreshToken = jwtUtil.generateRefreshToken(member);
            log.info("Tokens generated successfully for email: {}", email);

            // 토큰 응답
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.OK.value());
            objectMapper.writeValue(response.getWriter(), tokens);

        } catch (Exception e) {
            sendErrorResponse(response, e.getMessage());
        }
    }

    private String[] decodeBasicAuth(String header) {
        String base64Credentials = header.substring("Basic ".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                StandardCharsets.UTF_8);
        return credentials.split(":", 2);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, String> error = new HashMap<>();
        error.put("message", message);

        objectMapper.writeValue(response.getWriter(), error);
    }
}
