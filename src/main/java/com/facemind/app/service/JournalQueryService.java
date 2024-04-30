package com.facemind.app.service;

import com.facemind.app.converter.JournalConverter;
import com.facemind.app.domain.Journal;
import com.facemind.app.domain.Member;
import com.facemind.app.domain.Result;
import com.facemind.app.repository.JournalRepository;
import com.facemind.app.repository.ResultRepository;
import com.facemind.app.web.dto.JournalResponse;
import com.facemind.global.dateUtil.ConvertDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JournalQueryService {
    private final JournalRepository journalRepository;
    private final ResultRepository resultRepository;

    public JournalResponse.SingleDto findSingleJournal(Long journalId, Member member){
        Journal journal = journalRepository.findSingleJournal(journalId, member.getId());
        return JournalConverter.toSingleJournalDto(journal);
    }

    public JournalResponse.DailyDto findDailyJournal(String dateString, Member member) {
        LocalDate date = ConvertDate.toLocalDate(dateString);
        List<Result> results = resultRepository.findDailyResultAndJournal(date, member.getId());
        return JournalConverter.toDailyJournalDto(date, results);
    }
}
