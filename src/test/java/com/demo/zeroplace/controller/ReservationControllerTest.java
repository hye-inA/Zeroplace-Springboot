package com.demo.zeroplace.controller;

import com.demo.zeroplace.domain.Member;
import com.demo.zeroplace.domain.Reservation;
import com.demo.zeroplace.domain.Studyroom;
import com.demo.zeroplace.dto.request.ReservationCreateRequest;
import com.demo.zeroplace.repository.MemberRepository;
import com.demo.zeroplace.repository.ReservationRepository;
import com.demo.zeroplace.repository.StudyroomRepository;
import com.demo.zeroplace.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ReservationControllerTest {

    //ObjectMapper에 대한 빈 주입
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyroomRepository studyroomRepository;

    @BeforeEach
    void clean() {
        reservationRepository.deleteAll();
        memberRepository.deleteAll();
        studyroomRepository.deleteAll();
    }

    @Test
    @DisplayName("예약 생성 요청시 DB에 값이 저장된다")
    void createReservation() throws Exception {
        // given
        Member member = Member.builder()
                .name("테스트 사용자")
                .tel("010-1234-5678")
                .build();
        memberRepository.save(member);

        Studyroom studyroom = Studyroom.builder()
                .name("테스트 스터디룸")
                .capacity(4)
                .build();
        studyroomRepository.save(studyroom);

        ReservationCreateRequest request = ReservationCreateRequest.builder()
                .memberId(member.getId())
                .studyroomId(studyroom.getId())
                .startTime(LocalDateTime.now().plusHours(1))
                .endTime(LocalDateTime.now().plusHours(3))
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when - 이러한 요청을 했을때
        mockMvc.perform(post("/reservation")
                        .header("authorization", "hyein")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then - 결과 : DB의 행 한줄 생성 ( 데이터 1개 생성 )
        assertEquals(1L, reservationRepository.count());
        // 실제 DB에 잘 저장되었는지 확인
        Reservation reservation = reservationRepository.findAll().get(0);
        assertEquals(member.getId(), reservation.getMember().getId());
        assertEquals(studyroom.getId(), reservation.getStudyroom().getId());
        assertNotNull(reservation.getStartTime());
        assertNotNull(reservation.getEndTime());
    }

    @Test
    @DisplayName("예약 삭제")
    void test() throws Exception {
        // given
        Member member = Member.builder()
                .name("아무개씨")
                .tel("010-1234-5678")
                .build();
        memberRepository.save(member);

        Studyroom studyroom = Studyroom.builder()
                .name("스터디룸2")
                .capacity(4)
                .build();
        studyroomRepository.save(studyroom);

        Reservation reservation = Reservation.builder()
                .member(member)
                .studyroom(studyroom)
                .startTime(LocalDateTime.now().plusHours(1))
                .endTime(LocalDateTime.now().plusHours(3))
                .build();
        reservationRepository.save(reservation);

        // when
        mockMvc.perform(delete("/reservation/{reservationId}", reservation.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        Assertions.assertEquals(0, reservationRepository.count());
    }



}