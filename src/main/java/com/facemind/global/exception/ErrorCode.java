package com.facemind.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /*
     * 에러 코드 자유롭게 추가
     */
    INVALID_ARGUMENT_ERROR(HttpStatus.BAD_REQUEST, 400, "올바르지 않은 파라미터입니다."),
    INVALID_FORMAT_ERROR(HttpStatus.BAD_REQUEST,400, "올바르지 않은 포맷입니다."),
    INVALID_TYPE_ERROR(HttpStatus.BAD_REQUEST, 400, "올바르지 않은 타입입니다."),
    ILLEGAL_ARGUMENT_ERROR(HttpStatus.BAD_REQUEST, 400, "필수 파라미터가 없습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    INVALID_HTTP_METHOD(HttpStatus.METHOD_NOT_ALLOWED, 400, "잘못된 Http Method 요청입니다."),

    //AUTH (2000번대)
    TOKEN_NOT_VALIDATE(HttpStatus.BAD_REQUEST, 2001, "Token이 유효하지 않습니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, 2002, "이미 존재하는 email 입니다. "),

    //MEMBER (3000번대)
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, 3001, "존재하지 않는 회원입니다."),

    //RESULT (4000번대)
    RESULT_NOT_FOUND(HttpStatus.BAD_REQUEST, 4001, "존재하지 않는 검사 결과입니다."),

    //JOURNAL (5000번대)
    JOURNAL_NOT_FOUND(HttpStatus.BAD_REQUEST, 5001, "존재하지 않는 일지입니다."),

    //DATE (6000번대)
    DATE_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 6001, "날짜 parsing에 문제가 발생했습니다."),

    //SORT (7000번대)
    INVALID_SORT_TYPE(HttpStatus.BAD_REQUEST, 7001, "정렬 방식이 잘못되었습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
