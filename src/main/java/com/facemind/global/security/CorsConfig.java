package com.facemind.global.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);  // 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지 결정
        config.addAllowedOrigin("*");  // 모든 ip의 응답을 허용
        config.addAllowedHeader("*");  // 모든 http 요청 header에 응답을 허용하겠다.
        config.addAllowedMethod("*");  // 모든 post, get, put, delete, patch 요청을 허용하겠다.

        source.registerCorsConfiguration("**", config);  // 모든 경로에 대한 요청을 CORS 정책에 따라 처리
        return new CorsFilter(source);
    }
}
