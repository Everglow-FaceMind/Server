package com.facemind.app.config;

import com.facemind.app.repository.sortCalender.CalenderSort;
import com.facemind.app.repository.sortCalender.MaxCalenderSort;
import com.facemind.app.repository.sortCalender.MinCalenderSort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SortCalenderConfig {

    @PersistenceContext
    private final EntityManager em;

    @Bean
    public CalenderSort maxCalenderSort(){
        return new MaxCalenderSort(em);
    }

    @Bean
    public CalenderSort minCalenderSort(){
        return new MinCalenderSort(em);
    }
}
