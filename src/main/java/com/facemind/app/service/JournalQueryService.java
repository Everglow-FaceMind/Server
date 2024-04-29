package com.facemind.app.service;

import com.facemind.app.converter.JournalConverter;
import com.facemind.app.domain.Journal;
import com.facemind.app.domain.Member;
import com.facemind.app.repository.JournalRepository;
import com.facemind.app.web.dto.JournalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JournalQueryService {
    private final JournalRepository journalRepository;

    public JournalResponse.SingleDto findSingleJournal(Long journalId, Member member){
        Journal journal = journalRepository.findSingleJournal(journalId, member.getId());
        return JournalConverter.toSingleJournalDto(journal);
    }
}
