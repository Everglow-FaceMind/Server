package com.facemind;

import com.facemind.app.domain.Journal;
import com.facemind.app.domain.Member;
import com.facemind.app.domain.Result;
import com.facemind.app.domain.enums.Auth;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitService initService;

    @PostConstruct
    public void init(){
       //initService.initDB();
    }

    @Service
    @RequiredArgsConstructor
    @Transactional
    static class InitService{
        private final EntityManager em;

        public void initDB(){
            Member member1 = Member.builder()
                    .email("abc@naver.com")
                    .nickname("aaa")
                    .password("1111")
                    .alarmAllowance(true)
                    //.authority(Auth.ROLE_ADMIN)
                    .build();
            em.persist(member1);

            Result result1 = Result.builder()
                    .dateTime(LocalDateTime.now())
                    .heartRateAvg(80)
                    .heartRateMax(100)
                    .heartRateMin(60)
                    .stressRate(45)
                    .member(member1)
                    .build();
            em.persist(result1);

            Journal journal1 = Journal.builder()
                    .note("note")
                    .member(member1)
                    .result(result1)
                    .build();
            //em.persist(journal1);
        }

    }
}
