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
        private Double stressRate;
        private List<String> emotion;
        private List<String> cause;
        private String note;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DailyDto{
        private String date;
        private String dayOfTheWeek;
        private List<DailyJournals> journals;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyJournals{
        private Long resultId;
        private String time;
        private Integer heartRateMin;
        private Integer heartRateMax;
        private Integer heartRateAvg;
        private Double stressRate;
        private JournalDetail journalDetail;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JournalDetail{
        private Long journalId;
        private List<String> emotion;
        private List<String> cause;
        private String note;
    }

}
