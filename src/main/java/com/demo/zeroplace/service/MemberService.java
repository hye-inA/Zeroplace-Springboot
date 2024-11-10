package com.demo.zeroplace.service;

import com.demo.zeroplace.domain.Member;
import com.demo.zeroplace.repository.MemberRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.demo.zeroplace.dto.request.MemberCreateRequest;
import com.demo.zeroplace.dto.request.MemberUpdateRequest;
import com.demo.zeroplace.dto.response.MemberResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void saveMember(MemberCreateRequest request) {
        // Builder 패턴 적용 ( Service에서도 MemberCreateReqest JSON Body 값을 꺼내 Member에 생성자 형태로 넘겨주고 있기때문에 )
        Member member = Member.builder()
                .name(request.getName())
                .tel(request.getTel())
                .build();

        // Case1. 저장한 데이터 Entity -> response로 응답하기
        // return memberRepository.save(member);

        // Case2. 저장한 데이터의 primary_id -> response로 응답하기
        // 클라이언트에서는 수신한 id를 글 조회 API를 통해서 데이터를 수신받음
        // memberRepository.save(member);
        // return member.getId();

        // Case3. 응답 필요없음 -> 클라이언트에서 모든 Member 데이터 context를 잘 관리함
        memberRepository.save(member);
    }
    @Transactional
    public void updateMember(MemberUpdateRequest request) {
        Member member = memberRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);
        member.updateTel(request.getTel());
        memberRepository.save(member);
    }

    @Transactional
    public Member getMember(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(IllegalAccessError::new);

        return member;

    }
    @Transactional(readOnly = true)
    public List<MemberResponse> getMembers() {
        return memberRepository.findAll().stream()
                .map(member -> new MemberResponse(member.getId(), member.getName(), member.getTel()))
                .collect(Collectors.toList());
    }
    @Transactional
    public void deleteMember(String tel) {
        Member member = memberRepository.findByTel(tel);
        if (member == null) {
            throw new IllegalArgumentException();
        }

        memberRepository.delete(member);
    }
}
