package com.facemind.app.web.dto;

import lombok.*;


public class SuccessResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MessageDto{
        private String message;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class JournalIdDto{
        private Long journalId;
    }

}
