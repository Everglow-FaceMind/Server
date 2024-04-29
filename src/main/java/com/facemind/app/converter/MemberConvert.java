package com.facemind.app.converter;

import com.facemind.app.domain.Member;
import com.facemind.app.web.dto.MemberResponseDto;

public class MemberConvert {
    public static MemberResponseDto toMemberResponseDto(Member member){
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .introduction(member.getIntroduction())
                .nickname(member.getNickname())
                .build();
    }
}
