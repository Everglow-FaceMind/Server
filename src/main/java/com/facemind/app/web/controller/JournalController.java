package com.facemind.app.web.controller;

import com.facemind.app.domain.Member;
import com.facemind.app.repository.MemberRepository;
import com.facemind.app.service.JournalCommandService;
import com.facemind.app.web.dto.JournalRequest;
import com.facemind.app.web.dto.SuccessResponse;
import com.facemind.global.exception.ErrorCode;
import com.facemind.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/journals")
public class JournalController {
    private final JournalCommandService journalCommandService;
    private final MemberRepository memberRepository; // for test

    @PostMapping("/{result-id}")
    public ResponseEntity<SuccessResponse.JournalIdDto> createJournal(
            @PathVariable("result-id") Long id,
            @RequestBody JournalRequest.JournalOnlyDto journalOnlyDto
    ){
        // 멤버 찾아오는 로직 추가 (여기는 추후 삭제)
        Optional<Member> optionalMember = memberRepository.findById(1L);
        Member member;
        if (optionalMember.isPresent()){
            member = optionalMember.get();
        } else{
            throw new RestApiException(ErrorCode.MEMBER_NOT_FOUND);
        }

        Long journalId = journalCommandService.createJournal(member, id, journalOnlyDto);
        return new ResponseEntity<>(
                SuccessResponse.JournalIdDto.builder().journalId(journalId).build(),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{journal-id}")
    public ResponseEntity<SuccessResponse.MessageDto> modifyJournal(
            @PathVariable("journal-id") Long id,
            @RequestBody JournalRequest.JournalOnlyDto journalOnlyDto
    ){
        // 멤버 찾아오는 로직 추가 (여기는 추후 삭제)
        Optional<Member> optionalMember = memberRepository.findById(1L);
        Member member;
        if (optionalMember.isPresent()){
            member = optionalMember.get();
        } else{
            throw new RestApiException(ErrorCode.MEMBER_NOT_FOUND);
        }

        journalCommandService.modifyJournal(member, id, journalOnlyDto);
        return new ResponseEntity<>(
                SuccessResponse.MessageDto.builder().message("일지 수정 완료").build(),
                HttpStatus.OK
        );
    }

}
