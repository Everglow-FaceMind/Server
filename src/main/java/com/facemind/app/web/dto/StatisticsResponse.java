package com.facemind.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


public class StatisticsResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeeklyStatisticsDto{
        private String date;
        private String dayOfTheWeek;
        private List<HeartRateDto> heartRate;
        private List<WeeklyStressLevelDto> stressLevel;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HeartRateDto{
        private String date;
        private String dayOfTheWeek;
        private Integer heartRateMax;
        private Integer heartRateMin;
    }

//    @Getter
//    @Builder
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class StressLevelDto{
//        private Integer level;
//        private Integer percentage;
//    }

}
