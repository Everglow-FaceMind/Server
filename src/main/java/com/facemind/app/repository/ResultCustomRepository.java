package com.facemind.app.repository;

import com.facemind.app.domain.Result;

import java.time.LocalDate;
import java.util.List;

public interface ResultCustomRepository {

    List<Result> findDailyResultAndJournal(LocalDate date, Long id);
}
