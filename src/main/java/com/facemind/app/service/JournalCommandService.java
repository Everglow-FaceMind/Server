package com.facemind.app.service;

import com.facemind.app.domain.Cause;
import com.facemind.app.domain.Journal;
import com.facemind.app.domain.Member;
import com.facemind.app.domain.Result;
import com.facemind.app.repository.CauseRepository;
import com.facemind.app.repository.JournalRepository;
import com.facemind.app.repository.MemberRepository;
import jakarta.persistence.Temporal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.facemind.app.web.dto.JournalRequest.JournalOnlyDto;

@Service
@RequiredArgsConstructor
public class JournalService {
    private final JournalRepository journalRepository;
    private final MemberRepository memberRepository;
    private final CauseRepository causeRepository;
    private final Result

    /**
     * 일지 등록
     * @param member
     * @param resultId
     * @param journalOnlyDto
     * @return journal-id
     */
    @Transactional()
    public Long createJournal(Member member, Long resultId, JournalOnlyDto journalOnlyDto) {
        Result result =
        Journal journal = Journal.builder()
                .note()
                .
        return null;
    }

}
