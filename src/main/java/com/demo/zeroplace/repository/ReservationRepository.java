package com.demo.zeroplace.repository;

import com.demo.zeroplace.domain.Reservation;
import com.demo.zeroplace.domain.Studyroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByStudyroomAndStartTimeBetweenOrEndTimeBetween(Studyroom studyroom, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime startTime1, LocalDateTime endTime1);
}
