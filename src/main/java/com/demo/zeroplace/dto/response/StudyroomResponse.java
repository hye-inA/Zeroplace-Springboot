package com.demo.zeroplace.dto.response;

import com.demo.zeroplace.domain.studyroom.Studyroom;

public class StudyroomResponse {
    private long id;
    private String name;
    private int capacity;

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

    public int getCapacity() {
        return capacity;
    }
}
