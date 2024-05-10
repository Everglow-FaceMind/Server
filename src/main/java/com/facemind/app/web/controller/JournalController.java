package com.facemind.app.web.controller;

import com.facemind.app.domain.Member;
import com.facemind.app.service.AuthService;
import com.facemind.app.service.JournalCommandService;
import com.facemind.app.service.JournalQueryService;
import com.facemind.app.web.dto.JournalRequest;
import com.facemind.app.web.dto.JournalResponse;
import com.facemind.app.web.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/journals")
@Tag(name = "일지(journal) 컨트롤러", description = "header에 access token 필수")
public class JournalController {
    private final JournalCommandService journalCommandService;
    private final JournalQueryService journalQueryService;
    private final AuthService authService;

    @Operation(summary = "일지 단건 조회")
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

    @Operation(summary = "일자별(날짜별) 검사결과 & 일지 조회")
    @GetMapping("/daily")
    public ResponseEntity<JournalResponse.DailyDto> findDailyJournal(
        @RequestParam("date") String date,
        HttpServletRequest request
    ){
        Member member = authService.extractMemberId(request.getHeader("Authorization"));
        JournalResponse.DailyDto dailyDto = journalQueryService.findDailyJournal(date, member);
        return new ResponseEntity<>(
                dailyDto, HttpStatus.OK
        );
    }

    @Operation(summary = "새로운 일지 등록")
    @PostMapping("/{result-id}")
    public ResponseEntity<SuccessResponse.JournalIdDto> createJournal(
            @PathVariable("result-id") Long id,
            @RequestBody @Valid JournalRequest.JournalOnlyDto journalOnlyDto,
            HttpServletRequest request
    ){
        Member member = authService.extractMemberId(request.getHeader("Authorization"));
        Long journalId = journalCommandService.createJournal(member, id, journalOnlyDto);
        return new ResponseEntity<>(
                SuccessResponse.JournalIdDto.builder().journalId(journalId).build(),
                HttpStatus.OK
        );
    }

    @Operation(summary = "일지 수정")
    @PatchMapping("/{journal-id}")
    public ResponseEntity<SuccessResponse.MessageDto> modifyJournal(
            @PathVariable("journal-id") Long id,
            @RequestBody @Valid JournalRequest.JournalOnlyDto journalOnlyDto,
            HttpServletRequest request
    ){
        Member member = authService.extractMemberId(request.getHeader("Authorization"));
        journalCommandService.modifyJournal(member, id, journalOnlyDto);
        return new ResponseEntity<>(
                SuccessResponse.MessageDto.builder().message("일지 수정 완료").build(),
                HttpStatus.OK
        );
    }

    @Operation(summary = "일지 삭제")
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
