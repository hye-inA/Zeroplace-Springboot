package com.demo.zeroplace.controller;

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
    public String test(@RequestAttribute("userName") String userName) {
        log.info(">>>{}", userName);
        return "hello";
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

}
