package com.facemind.app.repository;

import com.facemind.app.domain.Journal;

public interface JournalCustomRepository {
    void deleteJournal(Long journalId);
    Journal findSingleJournal(Long journalId, Long memberId);
}
