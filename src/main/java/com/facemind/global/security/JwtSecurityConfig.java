package com.facemind.global.security;

import com.facemind.global.jwt.JwtFilter;
import com.facemind.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * JWT Filter를 추가
 */
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;

    /**
     * TokenProvider를 주입 받아서 JwtFilter를 통해 Security 로직에 필터 등록
     * configure 메서드를 오버라이드하여 Spring Security 설정을 구성 - JwtFilter를 생성하고 Spring Security 필터 체인에 등록
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        // JwtFilter를 UsernamePasswordAuthenticationFilter 앞에 추가로 등록
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
