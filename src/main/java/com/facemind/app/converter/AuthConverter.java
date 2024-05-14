package com.facemind.app.converter;

import com.facemind.app.domain.Member;
import com.facemind.app.web.dto.AuthResponse;
import com.facemind.global.jwt.dto.TokenDto;

public class AuthConverter {
    public static AuthResponse.LoginDTO toLoginDTO(TokenDto tokenDto, Member member){
        return AuthResponse.LoginDTO.builder()
                .tokenInfo(tokenDto)
                .memberInfo(AuthResponse.MemberInfo.builder()
                        .nickname(member.getNickname())
                        .introduction(member.getIntroduction())
                        .email(member.getEmail())
                        .build())
                .build();
    }
}
