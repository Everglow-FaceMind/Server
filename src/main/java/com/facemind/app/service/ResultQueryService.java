package com.facemind.app.service;

import com.facemind.app.domain.Member;
import com.facemind.app.repository.sortCalender.CalenderSort;
import com.facemind.app.web.dto.CalenderResponseDto;
import com.facemind.app.web.dto.ResultResponse;
import com.facemind.global.dateUtil.ConvertDate;
import com.facemind.global.exception.ErrorCode;
import com.facemind.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResultQueryService {
    private final Map<String, CalenderSort> calenderSortMap;

    public List<CalenderResponseDto> findCalenderInfo(LocalDate date, String sort, Member member){
        CalenderSort calenderSort = matchSortType(sort);
        LocalDate startDate = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = date.with(TemporalAdjusters.lastDayOfMonth());
        return calenderSort.sortStressRate(member.getId(), startDate, endDate);
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
