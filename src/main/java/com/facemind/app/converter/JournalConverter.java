package com.facemind.app.converter;

import com.facemind.app.domain.Cause;
import com.facemind.app.domain.Emotion;
import com.facemind.app.domain.Journal;
import com.facemind.app.web.dto.JournalResponse;
import com.facemind.global.dateUtil.DayOfWeek;

import java.time.format.DateTimeFormatter;

public class JournalConverter {
    public static JournalResponse.SingleDto toSingleJournalDto(Journal journal){
        return JournalResponse.SingleDto.builder()
                .date(journal.getResult().getDateTime().toLocalDate().toString())
                .dayOfTheWeek(
                        DayOfWeek.valueOf(journal.getResult().getDateTime().getDayOfWeek().toString()).getKoreanName())
                .time(journal.getResult().getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .heartRateMin(journal.getResult().getHeartRateMin())
                .heartRateMax(journal.getResult().getHeartRateMax())
                .heartRateAvg(journal.getResult().getHeartRateAvg())
                .stressLevel(journal.getResult().getStressLevel())
                .emotion(journal.getEmotions().stream().map(Emotion::getName).toList())
                .cause(journal.getCauses().stream().map(Cause::getName).toList())
                .note(journal.getNote())
                .build();
    }
}
