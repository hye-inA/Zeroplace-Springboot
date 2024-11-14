package com.demo.zeroplace.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(force = true,access = AccessLevel.PROTECTED)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyroom_id")
    private Studyroom studyroom;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Builder
    public Reservation(Long id, Member member, Studyroom studyroom, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.member = member;
        this.studyroom = studyroom;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
