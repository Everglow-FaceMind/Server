package com.facemind.app.converter;

import com.facemind.app.domain.Member;
import com.facemind.app.web.dto.CalenderResponseDto;
import com.facemind.app.web.dto.ResultResponse;
import com.facemind.app.web.dto.WeeklyHeartRateDto;
import com.facemind.app.web.dto.WeeklyStressLevelDto;
import com.facemind.global.dateUtil.DayOfWeek;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facemind.app.web.dto.ResultResponse.HomeDto;
import static com.facemind.app.web.dto.StatisticsResponse.HeartRateDto;
import static com.facemind.app.web.dto.StatisticsResponse.WeeklyStatisticsDto;

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

    public static WeeklyStatisticsDto toWeeklyStatisticsDto(
            LocalDate localDate, List<WeeklyHeartRateDto> heartRates, List<WeeklyStressLevelDto> stresses){
        return WeeklyStatisticsDto.builder()
                .date(localDate.toString())
                .dayOfTheWeek(DayOfWeek.valueOf(localDate.getDayOfWeek().toString()).getKoreanName())
                .heartRate(toHeartRateDto(heartRates))
                .stressLevel(stresses)
                .build();
    }

    public static List<HeartRateDto> toHeartRateDto(List<WeeklyHeartRateDto> heartRates) {
        return heartRates.stream().map(h ->
                HeartRateDto.builder()
                        .date(h.getDate().toString())
                        .dayOfTheWeek(DayOfWeek.valueOf(h.getDate().getDayOfWeek().toString()).getKoreanName())
                        .heartRateMax(h.getMaxHR())
                        .heartRateMin(h.getMinHR())
                        .build()
                ).toList();
    }

    public static List<WeeklyStressLevelDto> toWeeklyStressLevelDto(HashMap<Integer, Integer> levels){
        List<WeeklyStressLevelDto> results = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : levels.entrySet()){
            results.add(
                    WeeklyStressLevelDto.builder()
                            .level(entry.getKey())
                            .percentage(entry.getValue())
                            .build()
            );
        }
        return results;
    }

}
