package com.demo.zeroplace.domain;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String tel;

    protected Member() {
    }

    public Member(String name, String tel) {
        // TODO: if문을 이용한 데이터 검증 리펙토링 필요 -> Validation 의존성 사용
        /* 문제점 - 코드 반복, 필드 누락 위험, 검증할 요소가 생각보다 많음
        ex ) { "name": "          " }, { "name": ".....수십억 글자" } 로 들어간다면??
         */
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("name(%s)을 입력해주세요", name));
        }
        if(tel == null || tel.isBlank()) {
            throw new IllegalArgumentException(String.format("tel(%s)을 입력해주세요", name));
        }
        this.name = name;
        this.tel = tel;
    }
    public Long getId() { return  id; }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public void updateTel(String tel) { this.tel = tel;}

}
