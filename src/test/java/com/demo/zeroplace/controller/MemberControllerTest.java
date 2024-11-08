package com.demo.zeroplace.controller;

import com.demo.zeroplace.domain.Member;
import com.demo.zeroplace.dto.request.MemberCreateRequest;
import com.demo.zeroplace.repository.MemberRepository;
import com.demo.zeroplace.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PostMapping;


import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //DB 저장 테스트를 위한 주입
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void clean() {
        // 각 테스트 메서드들이 실행되기전에 항상 memberRepository를 전체적으로 삭제되도록 보장해줌
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("/member 요청시 사용자의 이름과 전화번호가 응답으로 들어간다")
    void saveMember() throws Exception {
        // 보낼 데이터 - 사용자 이름, 전화번호
        // given
        MemberCreateRequest request = MemberCreateRequest.builder()
                .name("이름")
                .tel("전화번호")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);


        // expected
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{}"))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("/member 요청시 잘못된 요청이 들어오면 JSON 형태 에러 응답을 보낸다")
    void saveMember2() throws Exception {
        // 보낼 데이터 - 사용자 이름, 전화번호

        // expected
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"\", \"tel\": \"전화번호\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(jsonPath("$.validation.name").value("이름을 입력해주세요"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/member 요청시 요청한 json이 DB에 값이 저장된다")
    void saveMember3() throws Exception {
        // 보낼 데이터 - 사용자 이름, 전화번호

        // when - 이러한 요청을 했을때
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"이름\", \"tel\": \"전화번호\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        // then - 결과 : DB의 행 한줄 생성 ( 데이터 1개 생성 )
        Assertions.assertEquals(1L, memberRepository.count());
        // 실제 DB에 잘 저장되었는지 확인
        Member member = memberRepository.findAll().get(0);
        Assertions.assertEquals("이름", member.getName());
        Assertions.assertEquals("전화번호", member.getTel());
    }
}