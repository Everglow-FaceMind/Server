package com.facemind.app.service;

import com.facemind.app.converter.ResultConverter;
import com.facemind.app.domain.Member;
import com.facemind.app.domain.Result;
import com.facemind.app.repository.result.ResultRepository;
import com.facemind.app.web.dto.TestResultRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResultCommandService {
    private final ResultRepository resultRepository;

    @Transactional
    public Result addTestResult(Member member, TestResultRequest.addTestResultDTO dto){
        Result newResult = ResultConverter.toResult(dto, member);
        return resultRepository.save(newResult);
    }


}
