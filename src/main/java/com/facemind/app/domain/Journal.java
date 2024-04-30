package com.facemind.app.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Journal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id")
    private Result result;

    @OneToMany(mappedBy = "journal", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<Emotion> emotions = new ArrayList<>();

    @OneToMany(mappedBy = "journal", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<Cause> causes = new ArrayList<>();

    public void modifyNote(String note){
        this.note = note;
    }

    public void addEmotion(Emotion emotion) {
        emotion.setJournal(this);
        this.emotions.add(emotion);
    }

    public void addCause(Cause cause){
        cause.setJournal(this);
        this.causes.add(cause);
    }

    public void setResult(Result result){
        this.result = result;
    }
}
