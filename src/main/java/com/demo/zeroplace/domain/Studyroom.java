package com.demo.zeroplace.domain;

import jakarta.persistence.*;

@Entity
public class Studyroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Integer 타입으로 변경
    // NULL값이 가능하지만, int일 경우 클라이언트가 값을 안 보낸 것인지, 실제로 0을 보낸 것인지 구분이 어려운 문제점 발생
    // TODO : capacity 필드에 대한 유효성 검사 필요 ( 수용인원은 3명이상 되어야함 )
    private Integer capacity;

    public Studyroom() { }

    public Studyroom(String name, Integer capacity) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException(String.format("name(%s)을 입력해주세요", name));
        }
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }
}
