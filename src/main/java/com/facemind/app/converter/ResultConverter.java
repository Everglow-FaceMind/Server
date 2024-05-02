package com.facemind.app.converter;

import com.facemind.app.domain.Member;
import com.facemind.app.web.dto.CalenderResponseDto;
import com.facemind.app.web.dto.ResultResponse;

import java.util.List;

import static com.facemind.app.web.dto.ResultResponse.HomeDto;

public class ResultConverter {
    public static HomeDto toHomeDto(Member member, List<CalenderResponseDto> results) {
        return HomeDto.builder()
                .nickname(member.getNickname())
                .results(toCalenderDto(results))
                .build();
    }

    public static List<ResultResponse.CalenderDto> toCalenderDto(List<CalenderResponseDto> results){
        return results.stream().map(r ->
                ResultResponse.CalenderDto.builder()
                        .date(r.getDate().toString())
                        .stressRate(r.getStressRate()).build()
        ).toList();
    }

    public static
}
