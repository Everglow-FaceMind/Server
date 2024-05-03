package com.facemind.global.security;

import com.facemind.global.jwt.JwtFilter;
import com.facemind.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * Spring Security에 필요한 설정
 */
@EnableWebSecurity  // spring security 방화벽을 켜겠다.
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig{
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // ~에 대한 경로의 요청을 무시
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .requestMatchers(new AntPathRequestMatcher("/favicon.ico"))
                .requestMatchers(new AntPathRequestMatcher( "/css/**"))
                .requestMatchers(new AntPathRequestMatcher( "/js/**"))
                .requestMatchers(new AntPathRequestMatcher( "/img/**"))
                .requestMatchers(new AntPathRequestMatcher( "/lib/**"))
                .requestMatchers(new AntPathRequestMatcher( "/swagger-ui/**"))
                .requestMatchers(new AntPathRequestMatcher( "/v3/api-docs/**"))
                .requestMatchers(new AntPathRequestMatcher("/health/**"))
                //.requestMatchers(new AntPathRequestMatcher("/journals/**"))
        );
    }


    /**
     * ⭐⭐⭐ Spring Security의 동작이 여기서 결정된다.
     * Spring Security의 보안 필터 체인을 구성하는 SecurityFilterChain 빈을 생성 - Spring Security 필터들의 설정과 연결을 정의
     * @param http
     * @param introspector
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                // exception handling 할 때 우리가 만든 클래스 투가
                .exceptionHandling((exceptionConfig) -> exceptionConfig.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)

                )
                .headers((headerConfig) ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                // 시큐리티는 기본적으로 세션을 사용
                // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                .sessionManagement((sessionManager) ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                )

                // 로그인, 회원가입 API는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new MvcRequestMatcher(introspector, "/auth/**")).permitAll()
                            .anyRequest().authenticated())
                        .httpBasic(Customizer.withDefaults())  // 나머지는 전부 인증 필요

                // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig 클래스 적용
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
                //.apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }
}
