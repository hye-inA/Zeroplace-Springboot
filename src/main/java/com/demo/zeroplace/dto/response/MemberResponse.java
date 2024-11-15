package com.demo.zeroplace.dto.response;

import com.demo.zeroplace.domain.Member;
import lombok.Builder;
import lombok.Getter;
/**
 * 서비스 정책에 맞는 클래스
 */
@Getter
@Builder
public class MemberResponse {

    private long id;
    private String name;
    private String tel;

    public MemberResponse(Member member){
        this.id = member.getId();
        this.name = member.getName();
        this.tel = member.getTel();
    }
    @Builder
    public MemberResponse(Long id, String name, String tel) {
        this.id = id;
        this.name = name.substring(0, Math.min(name.length(), 10));
        this.tel = tel;
    }


}
