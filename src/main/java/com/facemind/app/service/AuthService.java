package com.facemind.app.service;

import com.facemind.app.converter.MemberConvert;
import com.facemind.app.domain.Member;
import com.facemind.app.web.dto.LoginRequestDto;
import com.facemind.app.web.dto.MemberRequestDto;
import com.facemind.app.web.dto.MemberResponseDto;
import com.facemind.app.repository.MemberRepository;
import com.facemind.app.repository.RefreshTokenRepository;
import com.facemind.global.exception.ErrorCode;
import com.facemind.global.exception.RestApiException;
import com.facemind.global.jwt.RefreshToken;
import com.facemind.global.jwt.TokenProvider;
import com.facemind.global.jwt.dto.TokenDto;
import com.facemind.global.jwt.dto.TokenRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new RestApiException(ErrorCode.EMAIL_ALREADY_EXIST);
        }
        String password = passwordEncoder.encode(memberRequestDto.getPassword());

        Member member = Member.builder()
                .email(memberRequestDto.getEmail())
                .password(password)
                .nickname(memberRequestDto.getNickname())
                .alarmAllowance(memberRequestDto.getAlarmAllowance())
                .introduction(memberRequestDto.getIntroduction())
                .build();

        memberRepository.save(member);
        return MemberConvert.toMemberResponseDto(member);
    }

    /**
     * 1. 입력받은 이메일과 비밀번호로 `UsernamePasswordAuthenticationToken`을 생성하여 인증을 시도 <br>
     * 2. `AuthenticationManagerBuilder`를 사용하여 해당 토큰으로 인증을 수행 <br>
     * 3. 인증에 성공하면, `tokenProvider`를 사용하여 액세스 토큰과 리프레시 토큰을 생성 <br>
     * 4. 생성된 리프레시 토큰을 데이터베이스에 저장하고, 액세스 토큰과 리프레시 토큰을 포함한 `TokenDto`를 반환 <br>
     * @param loginRequestDto
     * @return TokenDto
     */
    @Transactional
    public TokenDto login(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken refreshToken = RefreshToken.builder()
                .email(authentication.getName())
                .refreshToken(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }

    /**
     * refreshToken을 사용하여 accessToken을 다시 발급
     * @param tokenRequestDto
     * @return
     */
    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RestApiException(ErrorCode.TOKEN_NOT_VALIDATE);
        }

        // 2. Access Token 에서 Member 정보 추출
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 유저 입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getRefreshToken().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

    /**
     * accessToken으로 member 찾기
     * @param bearerToken
     * @return member
     */
    @Transactional
    public Member extractMemberId(String bearerToken) {
        String accessToken = resolveToken(bearerToken);
        // 1. 토큰 검증
        if (!tokenProvider.validateToken(accessToken)) {
            throw new RestApiException(ErrorCode.TOKEN_NOT_VALIDATE);
        }

        // 2. Access Token 에서 member 정보 추출
        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        // 3. 존재하는 이메일인지 확인 및 반환
        return memberRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RestApiException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private String resolveToken(String accessToken){
        if(StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")){
            return accessToken.split(" ")[1].trim();
        }
        return null;
    }
}
