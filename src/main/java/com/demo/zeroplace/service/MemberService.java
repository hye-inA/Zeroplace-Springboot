package com.demo.zeroplace.service;

import com.demo.zeroplace.domain.Member;
import com.demo.zeroplace.repository.MemberRepository;
import org.springframework.stereotype.Service;
import com.demo.zeroplace.dto.request.MemberCreateRequest;
import com.demo.zeroplace.dto.request.MemberUpdateRequest;
import com.demo.zeroplace.dto.response.MemberResponse;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void saveMember(MemberCreateRequest request) {
        memberRepository.save(new Member(request.getName(), request.getTel()));
    }

    public void updateMember(MemberUpdateRequest request) {
        Member member = memberRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);
        member.updateTel(request.getTel());
        memberRepository.save(member);
    }

    public List<MemberResponse> getMember() {
        return memberRepository.findAll().stream()
                .map(member -> new MemberResponse(member.getId(), member.getName(), member.getTel()))
                .collect(Collectors.toList());
    }

    public void deleteMember(String tel) {
        Member member = memberRepository.findByTel(tel);
        if (member == null) {
            throw new IllegalArgumentException();
        }

        memberRepository.delete(member);
    }
}
