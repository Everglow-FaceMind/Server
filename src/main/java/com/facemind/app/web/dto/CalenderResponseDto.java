package com.facemind.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalenderResponseDto {
    private LocalDate date;
    private Double stressRate;

    public CalenderResponseDto(java.sql.Date date, Double stressRate) {
        this.date = date.toLocalDate(); // java.sql.Date를 LocalDate로 변환
        this.stressRate = stressRate;
    }
}
