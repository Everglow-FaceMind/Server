package com.facemind.global.dateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConvertDate {
    public static LocalDate toLocalDate(String dateString){
        // 날짜 문자열에서 쌍따옴표를 제거
        dateString = dateString.replace("\"", "");

        // DateTimeFormatter를 사용하여 LocalDate 오브젝트를 생성
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // LocalDate 객체를 사용해 자정 시각에 해당하는 LocalDateTime 객체를 반환
        return date;
    }
}
