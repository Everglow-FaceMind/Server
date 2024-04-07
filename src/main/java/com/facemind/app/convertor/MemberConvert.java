package com.facemind.app.convertor;

import com.facemind.app.domain.Member;
import com.facemind.app.dto.MemberResponseDto;

public class MemberConvert {
    public static MemberResponseDto toMemberResponseDto(Member member){
        return MemberResponseDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .password(member.getPassword())
                .auth(String.valueOf(member.getAuthority()))
                .build();
    }
}
