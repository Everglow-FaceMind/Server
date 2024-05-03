package com.facemind.app.service;

import com.facemind.app.converter.ResultConverter;
import com.facemind.app.domain.Member;
import com.facemind.app.domain.Result;
import com.facemind.app.repository.result.ResultRepository;
import com.facemind.app.repository.sortCalender.CalenderSort;
import com.facemind.app.web.dto.CalenderResponseDto;
import com.facemind.app.web.dto.WeeklyHeartRateDto;
import com.facemind.app.web.dto.WeeklyStressLevelDto;
import com.facemind.global.exception.ErrorCode;
import com.facemind.global.exception.RestApiException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResultQueryService {
    private final Map<String, CalenderSort> calenderSortMap;
    private final ResultRepository resultRepository;

    /**
     * 홈화면의 정보 (닉네임 & 캘린더 정보)를 반환
     */
    public List<CalenderResponseDto> findCalenderInfo(LocalDate date, String sort, Member member){
        CalenderSort calenderSort = matchSortType(sort);
        LocalDate startDate = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = date.with(TemporalAdjusters.lastDayOfMonth());
        return calenderSort.sortStressRate(member.getId(), startDate, endDate);
    }

    /**
     * 통계 - 일주일의 심박수 최대, 최소 가져오기
     */
    public List<WeeklyHeartRateDto> getWeeklyHeartRate(LocalDate date, Member member){
        LocalDate startDate = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return resultRepository.getWeeklyHeartRate(startDate, endDate, member.getId());
    }

    /**
     * 통계 - 일주일의 스트레스 지수 백분율로 나타내기
     */
    public List<WeeklyStressLevelDto> getWeeklyStressLevel(LocalDate date, Member member){
        LocalDate startDate = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        List<Result> results = resultRepository.getWeeklyStressRate(startDate, endDate, member.getId());
        return levelingStressRates(results);
    }

    /**
     * Lv.1 80 - 93 <br>
     * Lv.2 94 - 107 <br>
     * Lv.3 108 - 121 <br>
     * Lv.4 122 - 135 <br>
     * Lv.5 136 - 150 <br>
     */
    private List<WeeklyStressLevelDto> levelingStressRates(List<Result> results){
        HashMap<Integer, Integer> levels = new HashMap<>(){{
            put(1, 0); put(2, 0); put(3, 0); put(4, 0); put(5, 0);
        }};
        int total = 0;

        // {level : 횟수}
        for(Result r:results){
            total += 1;
            if (r.getStressRate() <= 93) levels.put(1, levels.get(1)+1);
            else if (r.getStressRate() <= 107) levels.put(2, levels.get(2)+1);
            else if (r.getStressRate() <= 121) levels.put(3, levels.get(3)+1);
            else if (r.getStressRate() <= 135) levels.put(4, levels.get(4)+1);
            else if (r.getStressRate() <= 150) levels.put(5, levels.get(5)+1);
        }

        // 출력
        for (Map.Entry<Integer, Integer> entry : levels.entrySet()) {
            System.out.println("Level " + entry.getKey() + ": " + entry.getValue() + "%");
        }

        // {level : Percentage}
        ArrayList<Map.Entry<Integer, Double>> remainders = new ArrayList<>();

        int sumOfIntegers = 0;
        for (Map.Entry<Integer, Integer> entry : levels.entrySet()) {
            double percentage = (double) entry.getValue() / total * 100;
            int integerPart = (int) percentage;
            double remainder = percentage - integerPart;

            levels.put(entry.getKey(), integerPart);
            remainders.add(Map.entry(entry.getKey(), remainder));

            sumOfIntegers += integerPart;
        }

        remainders.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        int difference = 100 - sumOfIntegers;
        for (int i = 0; i < difference; i++) {
            Map.Entry<Integer, Double> entry = remainders.get(i);
            levels.put(entry.getKey(), levels.get(entry.getKey()) + 1);
        }

        return ResultConverter.toWeeklyStressLevelDto(levels);
    }

    private CalenderSort matchSortType(String sort){
        if(sort.equals("min")){
            return calenderSortMap.get("minCalenderSort");
        } else if (sort.equals("max")) {
            return calenderSortMap.get("maxCalenderSort");
        } else{
            throw new RestApiException(ErrorCode.INVALID_SORT_TYPE);
        }
    }

}
