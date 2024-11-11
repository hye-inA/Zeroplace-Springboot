package com.demo.zeroplace.controller;

import com.demo.zeroplace.domain.Member;
import com.demo.zeroplace.dto.request.MemberCreateRequest;
import com.demo.zeroplace.repository.MemberRepository;
import com.demo.zeroplace.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    //ObjectMapper에 대한 빈 주입
    @Autowired
    private ObjectMapper objectMapper;

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

        String json = objectMapper.writeValueAsString(request);


        // expected
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andDo(print());
    }


    @Test
    @DisplayName("/member 요청시 잘못된 요청이 들어오면 JSON 형태 에러 응답을 보낸다")
    void saveMember2() throws Exception {
        // given
        MemberCreateRequest request = MemberCreateRequest.builder()
                .tel("전화번호")
                .build();

        String json = objectMapper.writeValueAsString(request);


        // expected
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(jsonPath("$.validation.name").value("이름을 입력해주세요"))
                .andDo(print());
    }

    @Test
    @DisplayName("/member 요청시 요청한 json이 DB에 값이 저장된다")
    void saveMemberJsonToDB() throws Exception {
        // given
        MemberCreateRequest request = MemberCreateRequest.builder()
                .name("이름")
                .tel("전화번호")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when - 이러한 요청을 했을때
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        // then - 결과 : DB의 행 한줄 생성 ( 데이터 1개 생성 )
        Assertions.assertEquals(1L, memberRepository.count());
        // 실제 DB에 잘 저장되었는지 확인
        Member member = memberRepository.findAll().get(0);
        Assertions.assertEquals("이름", member.getName());
        Assertions.assertEquals("전화번호", member.getTel());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        // given
        Member member = Member.builder()
                .name("hyein11111111")
                .tel("1111")
                .build();
        memberRepository.save(member);

        // 클라이언트 요구사항
        // json 응답에서 name값 길이를 최대 10글자로 해주세요
        // Member entity <-> MemberResponse class
        // expected


        // expected
        mockMvc.perform(get("/member/{memberId}", member.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(member.getId()))
                .andExpect(jsonPath("$.name").value("hyein11111"))
                .andExpect(jsonPath("$.tel").value("1111"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {
        // given
        Member member1 = Member.builder()
                .name("name1")
                .tel("tel1")
                .build();
        memberRepository.save(member1);

        Member member2 = Member.builder()
                .name("name2")
                .tel("tel2")
                .build();
        memberRepository.save(member2);

        // expected
        mockMvc.perform(get("/member")
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$.[0].id").value(member1.getId()))
                .andExpect(jsonPath("$.[0].name").value("name1"))
                .andExpect(jsonPath("$.[0].tel").value("tel1"))
                .andDo(print());


    }
}