package com.demo.zeroplace.dto.response;

import com.demo.zeroplace.domain.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@Builder
public class ReservationResponse {
    private Long id;
    private Long memberId;
    private String memberName;
    private Long studyroomId;
    private String studyroomName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.memberId = reservation.getMember().getId();
        this.memberName = reservation.getMember().getName();
        this.studyroomId = reservation.getStudyroom().getId();
        this.studyroomName = reservation.getStudyroom().getName();
        this.startTime = reservation.getStartTime();
        this.endTime = reservation.getEndTime();
    }

    @Builder
    public ReservationResponse(Long id, Long memberId, String memberName,
                               Long studyroomId, String studyroomName,
                               LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.memberId = memberId;
        this.memberName = memberName;
        this.studyroomId = studyroomId;
        this.studyroomName = studyroomName;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
