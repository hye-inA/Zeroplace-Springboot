package com.demo.zeroplace.domain.studyroom;

public class Studyroom {
    private String name;
    private int capacity;

    public Studyroom(String name, int capacity) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException(String.format("name(%s)을 입력해주세요", name));
        }
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
