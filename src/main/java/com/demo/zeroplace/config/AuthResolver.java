package com.demo.zeroplace.config;

import com.demo.zeroplace.config.data.MemberSession;
import com.demo.zeroplace.exception.Unauthorized;
import com.demo.zeroplace.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;
@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private static final String KEY = ;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {
        String jws = webRequest.getHeader("Authorization");

        if (jws == null || jws.equals("")){
            throw new Unauthorized();
        }
        byte[] decodeKey = Base64.getDecoder().decode(KEY);

        // 복호화
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(decodeKey)
                    .build()
                    .parseSignedClaims(jws);
            String memberId = claims.getBody().getSubject();
            return new MemberSession(Long.parseLong(memberId));

        } catch (JwtException e) {
            throw new Unauthorized();
        }

    }
}
