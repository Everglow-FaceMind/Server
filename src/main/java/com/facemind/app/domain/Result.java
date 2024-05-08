package com.facemind.app.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Result {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private Integer heartRateMax;

    @Column(nullable = false)
    private Integer heartRateMin;

    @Column(nullable = false)
    private Integer heartRateAvg;

    @Column(nullable = false)
    private Double stressRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "result")
    private Journal journal;

    public void setJournal(Journal journal){
        this.journal = journal;
        journal.setResult(this);
    }

}
