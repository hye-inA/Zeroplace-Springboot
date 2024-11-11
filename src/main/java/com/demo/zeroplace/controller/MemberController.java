package com.demo.zeroplace.controller;

import com.demo.zeroplace.dto.request.MemberCreateRequest;
import com.demo.zeroplace.dto.request.MemberUpdateRequest;
import com.demo.zeroplace.dto.response.MemberResponse;
import com.demo.zeroplace.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member")
    public void saveMember(@RequestBody @Valid MemberCreateRequest request) {
        memberService.saveMember(request);
    }

    @GetMapping("/member/{memberId}")
    public MemberResponse getMember(@PathVariable Long memberId ) {
        return memberService.getMember(memberId);
    }

    @GetMapping("/member")
    public List<MemberResponse> getList() {
        return memberService.getList();
    }

    // TODO : 사용자 존재 여부 확인 쿼리 구체화, 구체적인 예외 클래스 구현
    @PutMapping("/member")
    public void updateMember(@RequestBody MemberUpdateRequest request) {
        memberService.updateMember(request);
    }

    //쿼리 파라미터가 1개라서 객체를 사용하지 않고 @RequestParam 사용
    // TODO : 사용자의 이름도 변경될 가능성 있는 데이터임으로 DTO로 변경
    @DeleteMapping("/member")
    public void deleteMember(@RequestParam String tel) {
       memberService.deleteMember(tel);
    }

}
