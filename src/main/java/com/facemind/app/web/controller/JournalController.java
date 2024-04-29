package com.facemind.app.web.controller;

import com.facemind.app.domain.Member;
import com.facemind.app.service.AuthService;
import com.facemind.app.service.JournalCommandService;
import com.facemind.app.service.JournalQueryService;
import com.facemind.app.web.dto.JournalRequest;
import com.facemind.app.web.dto.JournalResponse;
import com.facemind.app.web.dto.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/journals")
public class JournalController {
    private final JournalCommandService journalCommandService;
    private final JournalQueryService journalQueryService;
    private final AuthService authService;

    @GetMapping("/{journal-id}")
    public ResponseEntity<JournalResponse.SingleDto> findSingleJournal(
            @PathVariable("journal-id") Long id,
            HttpServletRequest request
    ){
        Member member = authService.extractMemberId(request.getHeader("Authorization"));
        JournalResponse.SingleDto singleDto = journalQueryService.findSingleJournal(id, member);
        return new ResponseEntity<>(
                singleDto, HttpStatus.OK
        );
    }

    @PostMapping("/{result-id}")
    public ResponseEntity<SuccessResponse.JournalIdDto> createJournal(
            @PathVariable("result-id") Long id,
            @RequestBody JournalRequest.JournalOnlyDto journalOnlyDto,
            HttpServletRequest request
    ){
        Member member = authService.extractMemberId(request.getHeader("Authorization"));
        Long journalId = journalCommandService.createJournal(member, id, journalOnlyDto);
        return new ResponseEntity<>(
                SuccessResponse.JournalIdDto.builder().journalId(journalId).build(),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{journal-id}")
    public ResponseEntity<SuccessResponse.MessageDto> modifyJournal(
            @PathVariable("journal-id") Long id,
            @RequestBody JournalRequest.JournalOnlyDto journalOnlyDto,
            HttpServletRequest request
    ){
        Member member = authService.extractMemberId(request.getHeader("Authorization"));
        journalCommandService.modifyJournal(member, id, journalOnlyDto);
        return new ResponseEntity<>(
                SuccessResponse.MessageDto.builder().message("일지 수정 완료").build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{journal-id}")
    public ResponseEntity<SuccessResponse.MessageDto> deleteJournal(
            @PathVariable("journal-id") Long id,
            HttpServletRequest request
    ){
        Member member = authService.extractMemberId(request.getHeader("Authorization"));
        journalCommandService.deleteJournal(id);
        return new ResponseEntity<>(
                SuccessResponse.MessageDto.builder().message("일지 삭제 완료").build(),
                HttpStatus.OK
        );
    }

}
