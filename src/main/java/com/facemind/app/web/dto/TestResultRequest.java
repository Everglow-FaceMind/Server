package com.facemind.app.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class TestResultRequest {
    @Getter
    public static class addTestResultDTO{
        @NotBlank
        private String date;
        @NotBlank
        private String time;
        @NotNull
        private Integer heartRateMin;
        @NotNull
        private Integer heartRateMax;
        @NotNull
        private Integer heartRateAvg;
        @NotNull
        private Double stressRate;
    }
}
