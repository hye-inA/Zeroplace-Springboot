package com.demo.zeroplace.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationCreateRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long studyroomId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;
    @Builder
    public ReservationCreateRequest(Long memberId, Long studyroomId, LocalDateTime startTime, LocalDateTime endTime) {
        this.memberId = memberId;
        this.studyroomId = studyroomId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
