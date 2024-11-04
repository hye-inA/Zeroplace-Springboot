package com.demo.zeroplace.dto.response;

import com.demo.zeroplace.domain.Studyroom;

public class StudyroomResponse {
    private long id;
    private String name;
    private Integer capacity;

    public StudyroomResponse(long id, String name, Integer capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public StudyroomResponse(long id, Studyroom studyroom) {
        this.id = id;
        this.name = studyroom.getName();
        this.capacity = studyroom.getCapacity();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }
}
