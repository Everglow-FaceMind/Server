package com.facemind.app.converter;

import com.facemind.app.domain.Cause;
import com.facemind.app.domain.Emotion;
import com.facemind.app.domain.Journal;
import com.facemind.app.domain.Result;
import com.facemind.app.web.dto.JournalResponse;
import com.facemind.global.dateUtil.DayOfWeek;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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

    public static JournalResponse.DailyDto toDailyJournalDto(LocalDate date, List<Result> results){
        return JournalResponse.DailyDto.builder()
                .date(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .dayOfTheWeek(DayOfWeek.valueOf(date.getDayOfWeek().toString()).getKoreanName())
                .journals(toDailyJournals(results))
                .build();
    }

    public static List<JournalResponse.DailyJournals> toDailyJournals(List<Result> results){
        return results.stream().map(r ->
                JournalResponse.DailyJournals.builder()
                        .time(r.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                        .heartRateMin(r.getHeartRateMin())
                        .heartRateMax(r.getHeartRateMax())
                        .heartRateAvg(r.getHeartRateAvg())
                        .stressLevel(r.getStressLevel())
                        .journalDetail(toJournalDetail(r))
                        .build()
        ).collect(Collectors.toList());
    }

    public static JournalResponse.JournalDetail toJournalDetail(Result r){
        Journal j = r.getJournal();
        if(j == null)
            return null;
        else {
            return JournalResponse.JournalDetail.builder()
                    .journalId(j.getId())
                    .emotion(j.getEmotions().stream().map(Emotion::getName).toList())
                    .cause(j.getCauses().stream().map(Cause::getName).toList())
                    .note(j.getNote())
                    .build();
        }
    }
}
