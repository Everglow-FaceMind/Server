package com.facemind.app.repository;

import com.facemind.app.domain.Journal;
import com.facemind.app.domain.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface JournalCustomRepository {
    void deleteJournal(Long journalId);
    Journal findSingleJournal(Long journalId, Long memberId);
}
