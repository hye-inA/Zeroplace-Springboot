package com.demo.zeroplace.domain.member;

public class Member {
    private String name;
    private String tel;

    public Member(String name, String tel) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("name(%s)을 입력해주세요", name));
        }
        if(tel == null || tel.isBlank()) {
            throw new IllegalArgumentException(String.format("tel(%s)을 입력해주세요", name));
        }
        this.name = name;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }
}
