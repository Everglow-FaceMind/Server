package com.facemind.app.web.controller;

import com.facemind.app.converter.ResultConverter;
import com.facemind.app.domain.Member;
import com.facemind.app.service.AuthService;
import com.facemind.app.service.ResultQueryService;
import com.facemind.app.web.dto.CalenderResponseDto;
import com.facemind.app.web.dto.ResultResponse;
import com.facemind.global.dateUtil.ConvertDate;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/result")
@Tag(name = "검사 결과 컨트롤러", description = "header에 access token 필수")
public class ResultController {
    private final ResultQueryService resultQueryService;
    private final AuthService authService;

    @GetMapping("/home")
    public ResponseEntity<ResultResponse.HomeDto> findHomeInfo(
            @RequestParam(required = false) String date,
            @RequestParam(defaultValue = "max") String sort,
            HttpServletRequest request
    ){
        Member member = authService.extractMemberId(request.getHeader("Authorization"));
        LocalDate localDate;
        if(date == null){localDate = LocalDate.now();}
        else{localDate = ConvertDate.toLocalDate(date);}
        List<CalenderResponseDto> results = resultQueryService.findCalenderInfo(localDate, sort, member);
        return new ResponseEntity<>(
                ResultConverter.toHomeDto(member, results), HttpStatus.OK
        );
    }
}
