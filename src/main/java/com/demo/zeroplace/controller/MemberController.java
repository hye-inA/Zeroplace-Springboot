package com.demo.zeroplace.controller;

import java.util.ArrayList;
import java.util.List;

import com.demo.zeroplace.domain.member.Member;
import com.demo.zeroplace.dto.request.MemberCreateRequest;
import com.demo.zeroplace.dto.response.MemberResponse;
import org.springframework.web.bind.annotation.*;


@RestController
public class MemberController {

    private final List<Member> members = new ArrayList<>();

    @PostMapping("/member")
    public void saveMember(@RequestBody MemberCreateRequest request){
        Member newMember = new Member(request.getName(), request.getTel());
        members.add(newMember);
    }

    @GetMapping("/user")
    public List<MemberResponse> getMembers() {
        List<MemberResponse> responses = new ArrayList<>();
        for(int i = 0; i < members.size(); i++){
            responses.add(new MemberResponse(i + 1, members.get(i)));
        }
        return responses;
    }

}
