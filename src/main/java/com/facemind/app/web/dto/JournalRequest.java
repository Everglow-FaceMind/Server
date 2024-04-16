package com.facemind.app.web.dto;

import lombok.*;

import java.util.List;

public class JournalRequest {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class JournalOnlyDto{
        private List<String> emotions;
        private List<String> cause;
        private String note;
    }

}
