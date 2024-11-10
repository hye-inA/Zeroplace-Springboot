package com.demo.zeroplace.dto.response;

import com.demo.zeroplace.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {

    private long id;
    private String name;
    private String tel;

    public MemberResponse(Long id, String name, String tel) {
        this.id = id;
        this.name = name;
        this.tel = tel;
    }

    public MemberResponse(long id, Member member) {
        this.id = id;
        this.name = member.getName();
        this.tel = member.getTel();
    }

    public MemberResponse(Member member) {
        this.id = id;
        this.name = name;
        this.tel = tel;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }
}
