package com.facemind.app.service;

import com.facemind.app.domain.Member;
import com.facemind.app.repository.result.ResultRepository;
import com.facemind.app.repository.sortCalender.CalenderSort;
import com.facemind.app.web.dto.CalenderResponseDto;
import com.facemind.app.web.dto.WeeklyHeartRateDto;
import com.facemind.app.web.dto.WeeklyStressLevelDto;
import com.facemind.global.exception.ErrorCode;
import com.facemind.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

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
     * 통계 중 일주일의 심박수 최대, 최소 가져오기
     */
    public List<WeeklyHeartRateDto> getWeeklyHeartRate(LocalDate date, Member member){
        LocalDate startDate = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return resultRepository.getWeeklyHeartRate(startDate, endDate, member.getId());
    }

    public List<WeeklyStressLevelDto> getWeeklyStressLevel(LocalDate localDate, Member member){

        return null;
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
