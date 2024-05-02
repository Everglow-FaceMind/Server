package com.facemind.app.repository.sortCalender;

import com.facemind.app.web.dto.CalenderResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface CalenderSort {
    List<CalenderResponseDto> sortStressRate(Long memberId, LocalDate startDate, LocalDate endDate);
}
