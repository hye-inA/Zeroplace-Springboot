package com.demo.zeroplace.service;

import com.demo.zeroplace.domain.Member;
import com.demo.zeroplace.dto.request.MemberCreateRequest;
import com.demo.zeroplace.dto.response.MemberResponse;
import com.demo.zeroplace.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
class MemberServiceTest {


    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 저장")
    void test1() {
        // given
        MemberCreateRequest request = MemberCreateRequest.builder()
                .name("이름")
                .tel("전화번호")
                .build();

        // when
        memberService.saveMember(request);

        // then
        assertEquals(1L, memberRepository.count());
        Member member = memberRepository.findAll().get(0);
        assertEquals("이름", member.getName());
        assertEquals("전화번호", member.getTel());

    }

    @Test
    @DisplayName("사용자 1명 조회")
    void test2() {
        // given
        Member requestMember = Member.builder()
                .name("hyein")
                .tel("1111")
                .build();
        memberRepository.save(requestMember);

        // when
        MemberResponse response = memberService.getMember(requestMember.getId());

        // then
        assertNotNull(response);
        assertEquals(1L, memberRepository.count());
        assertEquals("hyein", response.getName());
        assertEquals("1111", response.getTel());
    }

    @Test
    @DisplayName("사용자 여러명 조회")
    void test3() {
        // given
        Member requestMember1 = Member.builder()
                .name("hyein")
                .tel("1111")
                .build();
        memberRepository.save(requestMember1);

        Member requestMember2 = Member.builder()
                .name("hymin")
                .tel("2222")
                .build();
        memberRepository.save(requestMember2);

        // when
        List<MemberResponse> response = memberService.getList();

        // then
        assertEquals(2L, memberRepository.count());
    }

}