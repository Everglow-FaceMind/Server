package com.facemind.app.web.dto;

import com.facemind.global.jwt.dto.TokenDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthResponse {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO{
        private TokenDto tokenInfo;
        private MemberInfo memberInfo;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberInfo{
        private String email;
        private String nickname;
        private String introduction;
    }
}
