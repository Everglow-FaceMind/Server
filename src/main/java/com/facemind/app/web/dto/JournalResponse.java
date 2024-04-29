package com.facemind.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class JournalResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SingleDto{
        private String date;
        private String dayOfTheWeek;
        private String time;
        private Integer heartRateMin;
        private Integer heartRateMax;
        private Integer heartRateAvg;
        private Integer stressLevel;
        private List<String> emotion;
        private List<String> cause;
        private String note;
    }

    public static class DailyDto{

    }
}
