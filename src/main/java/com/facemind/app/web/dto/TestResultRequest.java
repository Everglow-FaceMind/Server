package com.facemind.app.web.dto;

import lombok.Getter;

public class TestResultRequest {
    @Getter
    public static class addTestResultDTO{
        private String date;
        private String time;
        private Integer heartRateMin;
        private Integer heartRateMax;
        private Integer heartRateAvg;
        private Double stressRate;
    }
}
