package com.demo.zeroplace.controller;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import com.demo.zeroplace.dto.request.MemberCreateRequest;
import com.demo.zeroplace.dto.request.MemberUpdateRequest;
import com.demo.zeroplace.dto.response.MemberResponse;
import com.demo.zeroplace.service.MemberService;


@RestController
public class MemberController {
    private final JdbcTemplate jdbcTemplate;
    private final MemberService memberService;

    public MemberController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.memberService = new MemberService(jdbcTemplate);
    }

    @PostMapping("/member")
    public void saveMember(@RequestBody MemberCreateRequest request) {
       memberService.saveMember(request);
    }

    @GetMapping("/member")
    public List<MemberResponse> getMembers() {
        return memberService.getMember();
    }

    // TODO : 사용자 존재 여부 확인 쿼리 구체화, 구체적인 예외 클래스 구현
    @PutMapping("/member")
    public void updateMember(@RequestBody MemberUpdateRequest request) {
        memberService.updateMember(jdbcTemplate, request);

    }

    //쿼리 파라미터가 1개라서 객체를 사용하지 않고 @RequestParam 사용
    // TODO : 사용자의 이름도 변경될 가능성 있는 데이터임으로 DTO로 변경
    @DeleteMapping("/member")
    public void deleteMember(@RequestParam String tel) {
       memberService.deleteMember(tel);
    }

}
