package com.facemind.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Spring Request 앞단에 붙일 Custom Filter
 */
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;


    /**
     * 실제 필터링 로직이 구현된 매서드 <br>
     * JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    // ⭐
    // 클라이언트의 요청이 들어오면 이 메서드가 호출됨
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. Request Header에서 토큰을 꺼낸다
        String jwt = resolveToken(request);

        // 2. validateToken으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication을 가져와서 SecurityContext에 저장
        if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
            // 해당 토큰에서 Authentication 객체 (사용자 인증 및 권한 정보)를 가져옴
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            // 가져온 `Authentication` 객체를 현재 쓰레드의 `SecurityContext`에 저장
            //이를 통해 현재 사용자의 인증 및 권한 정보를 애플리케이션에서 사용할 수 있게 됨
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Request Header에서 jwt 토큰 정보 추출 (bearer를 날리고 뒤의 것만 사용)
     * @param request
     * @return
     */
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.split(" ")[1].trim();
        }
        return null;
    }
}
