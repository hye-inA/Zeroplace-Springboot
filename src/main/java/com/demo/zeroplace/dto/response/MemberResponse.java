package com.demo.zeroplace.dto.response;

import com.demo.zeroplace.domain.member.Member;

public class MemberResponse {

    private long id;
    private String name;
    private String tel;

    public MemberResponse(long id, Member member) {
        this.id = id;
        this.name = member.getName();
        this.tel = member.getTel();
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
