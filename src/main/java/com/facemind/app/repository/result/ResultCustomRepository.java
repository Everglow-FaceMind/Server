package com.facemind.app.repository.result;

import com.facemind.app.domain.Result;
import com.facemind.app.web.dto.WeeklyHeartRateDto;

import java.time.LocalDate;
import java.util.List;

public interface ResultCustomRepository {

    List<Result> findDailyResultAndJournal(LocalDate date, Long id);

    List<WeeklyHeartRateDto> getWeeklyHeartRate(LocalDate startDate, LocalDate endDate, Long id);

    List<Result> getWeeklyStressRate(LocalDate startDate, LocalDate endDate, Long id);
}
