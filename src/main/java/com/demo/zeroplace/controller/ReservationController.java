package com.demo.zeroplace.controller;

import com.demo.zeroplace.config.data.UserSession;
import com.demo.zeroplace.dto.request.ReservationCreateRequest;
import com.demo.zeroplace.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/test")
    public Long test(UserSession userSession) {
        log.info(">>>{}", userSession.id);
        return userSession.id;
    }

    @GetMapping("/test2")
    public String test2() {
        return "인증이 필요없는 페이지";
    }

//    @GetMapping("/exceptapi")
//    public String exceptapi() {
//        return "exceptapi";
//    }

    @PostMapping("/reservation")
    public void createReservation(@RequestBody @Valid ReservationCreateRequest request, @RequestHeader() String authorization) {
        if (authorization.equals("hyein")) {
        reservationService.createReservation(request);
        }
    }

    @DeleteMapping("/reservation/{reservationId}")
    public void deleteReservation(@PathVariable Long reservationId){
        reservationService.delete(reservationId);
    }

}
