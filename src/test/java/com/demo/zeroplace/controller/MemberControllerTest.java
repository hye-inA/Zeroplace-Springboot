package com.demo.zeroplace.controller;

import com.demo.zeroplace.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MemberController.class)
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("/member 요청시 사용자의 이름과 전화번호가 응답으로 들어간다")
    void saveMember() throws Exception {
        // 보낼 데이터 - 사용자 이름, 전화번호

        // expected
        mockMvc.perform(post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"이름\", \"tel\": \"전화번호\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/member 요청시 사용자의 이름에 빈값 또는 null값이 입력되면 에러메세지를 응답으로 보낸다")
    void saveMember2() throws Exception {
        // 보낼 데이터 - 사용자 이름, 전화번호

        // expected
        mockMvc.perform(post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": null, \"tel\": \"전화번호\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name").value("이름을 입력해주세요"))
                .andDo(MockMvcResultHandlers.print());
    }
}