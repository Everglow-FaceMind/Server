package com.facemind.app.web.dto;

import com.facemind.global.annotation.ListNotEmpty;
import lombok.Getter;

import java.util.List;

public class JournalRequest {

    @Getter
    public static class JournalOnlyDto{
        @ListNotEmpty
        private List<String> emotions;
        @ListNotEmpty
        private List<String> cause;
        private String note;
    }

}
