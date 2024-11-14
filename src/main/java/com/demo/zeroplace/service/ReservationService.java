package com.demo.zeroplace.service;

import com.demo.zeroplace.domain.Member;
import com.demo.zeroplace.domain.Reservation;
import com.demo.zeroplace.domain.Studyroom;
import com.demo.zeroplace.dto.request.ReservationCreateRequest;
import com.demo.zeroplace.repository.MemberRepository;
import com.demo.zeroplace.repository.ReservationRepository;
import com.demo.zeroplace.repository.StudyroomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service

public class ReservationService {


    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final StudyroomRepository studyroomRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              MemberRepository memberRepository,
                              StudyroomRepository studyroomRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.studyroomRepository = studyroomRepository;
    }

    @Transactional
    public void createReservation(ReservationCreateRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        Studyroom studyroom = studyroomRepository.findById(request.getStudyroomId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 스터디룸입니다."));

        Reservation reservation = Reservation.builder()
                .member(member)
                .studyroom(studyroom)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        reservationRepository.save(reservation);
    }

    private void validateReservationTime(Studyroom studyroom, LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("종료 시간이 시작 시간보다 빠를 수 없습니다");
        }

        // 중복 예약 체크 로직
        boolean exists = reservationRepository.existsByStudyroomAndStartTimeBetweenOrEndTimeBetween(
                studyroom, startTime, endTime, startTime, endTime);
        if (exists) {
            throw new IllegalArgumentException("해당 시간에 이미 예약이 존재합니다");
        }
    }
}
