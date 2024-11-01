package com.demo.zeroplace.service;

import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

import com.demo.zeroplace.dto.request.MemberCreateRequest;
import com.demo.zeroplace.dto.request.MemberUpdateRequest;
import com.demo.zeroplace.dto.response.MemberResponse;
import com.demo.zeroplace.repository.MemberRepository;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(JdbcTemplate jdbcTemplate) {
        this.memberRepository = new MemberRepository(jdbcTemplate);
    }

    public void saveMember(MemberCreateRequest request){
        memberRepository.saveMember(request.getName(), request.getTel());
    }

    public void updateMember(JdbcTemplate jdbcTemplate, MemberUpdateRequest request) {
        if (memberRepository.isUserNotExist(jdbcTemplate, request.getId())) {
            throw new IllegalArgumentException();
        }

        memberRepository.updateMemberTel(jdbcTemplate, request.getTel(), request.getId());
    }

    public List<MemberResponse> getMember(){
        return memberRepository.getUserResponse();
    }

    public void deleteMember(String tel){

        if (memberRepository.isUserNotExist(tel)) {
            throw new IllegalArgumentException();
        }

        memberRepository.deleteMemberByTel(tel);
    }
}
