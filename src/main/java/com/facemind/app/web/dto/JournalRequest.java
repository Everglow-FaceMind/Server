package com.facemind.app.web.dto;

import lombok.*;

import java.util.List;

public class JournalRequestDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class JournalOnlyRequestDto{
        private List<String> emotions;
        private List<String> cause;
        private String note;
    }

}
