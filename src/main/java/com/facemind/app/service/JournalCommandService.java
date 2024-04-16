package com.facemind.app.service;

import com.facemind.app.domain.*;
import com.facemind.app.repository.JournalImplRepository;
import com.facemind.app.repository.JournalRepository;
import com.facemind.app.repository.ResultRepository;
import com.facemind.global.exception.ErrorCode;
import com.facemind.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.facemind.app.web.dto.JournalRequest.JournalOnlyDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class JournalCommandService {
    private final JournalRepository journalRepository;
    private final JournalImplRepository journalImplRepository;
    private final ResultRepository resultRepository;

    /**
     * 일지 등록
     * @param member
     * @param resultId
     * @param journalOnlyDto
     * @return journal-id
     */
    @Transactional
    public Long createJournal(Member member, Long resultId, JournalOnlyDto journalOnlyDto) {
        Journal journal = Journal.builder()
                .note(journalOnlyDto.getNote())
                .member(member)
                .result(findResultById(resultId))
                .build();
        mapEmotionAndJournal(journalOnlyDto.getEmotions(), journal);
        mapCauseAndJournal(journalOnlyDto.getCause(), journal);
        return journalRepository.save(journal).getId();
    }

    /**
     * 일지 수정
     * @param member
     * @param journalId
     * @param journalOnlyDto
     */
    @Transactional
    public void modifyJournal(Member member, Long journalId, JournalOnlyDto journalOnlyDto) {
        Journal journal = findJournalById(journalId);
        journal.modifyNote(journalOnlyDto.getNote());
        // orphan 삭제하고, jpql 작성 (성능 최적화)
        journal.getEmotions().clear();
        journal.getCauses().clear();
        mapEmotionAndJournal(journalOnlyDto.getEmotions(), journal);
        mapCauseAndJournal(journalOnlyDto.getCause(), journal);
    }

    /**
     * 일지 삭제
     * @param journalId
     */
    @Transactional
    public void deleteJournal(Long journalId) {
        Journal journal = findJournalById(journalId);
        //이거 때문에 e, c의 select 쿼리까지 나감. (jpql 수정)
        journalRepository.deleteById(journal.getId());
    }

    private Result findResultById(Long resultId){
        Optional<Result> optionalResult = resultRepository.findById(resultId);
        if(optionalResult.isPresent()){
            return optionalResult.get();
        } else{
            throw new RestApiException(ErrorCode.RESULT_NOT_FOUND);
        }
    }

    private Journal findJournalById(Long journalId){
        Optional<Journal> optionalJournal = journalRepository.findById(journalId);
        if(optionalJournal.isPresent()){
            return optionalJournal.get();
        } else{
            throw new RestApiException(ErrorCode.JOURNAL_NOT_FOUND);
        }
    }

    private void mapEmotionAndJournal(List<String> emotionNames, Journal journal){
        emotionNames.forEach(emotionName ->{
            Emotion emotion = Emotion.builder().name(emotionName).build();
            journal.addEmotion(emotion);
        });
    }

    private void mapCauseAndJournal(List<String> causes, Journal journal){
        causes.forEach(causeName -> {
            Cause cause = Cause.builder().name(causeName).build();
            journal.addCause(cause);
        });
    }

}
