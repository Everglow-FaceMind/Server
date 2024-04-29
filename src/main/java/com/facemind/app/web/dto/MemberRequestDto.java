package com.facemind.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {
    private String email;
    private String password;
    private String nickname;
    private Boolean alarmAllowance;
    private String introduction;
}
