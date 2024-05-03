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
public class WeeklyHeartRateDto {
    private LocalDate date;
    private Integer maxHR;
    private Integer minHR;

    public WeeklyHeartRateDto(java.sql.Date date, Integer maxHR, Integer minHR){
        this.date = date.toLocalDate();
        this.maxHR = maxHR;
        this.minHR = minHR;
    }

}
