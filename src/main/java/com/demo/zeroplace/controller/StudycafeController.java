package com.demo.zeroplace.controller;

import java.util.ArrayList;
import java.util.List;

import com.demo.zeroplace.domain.studyroom.Studyroom;
import com.demo.zeroplace.dto.request.StudyroomCreateRequest;
import com.demo.zeroplace.dto.response.StudyroomResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



public class StudycafeController {
    private final List<Studyroom> studyrooms = new ArrayList<>();

    @PostMapping("/studyroom")
    public void saveStudyroom(@RequestBody StudyroomCreateRequest request){
        Studyroom newStudyroom = new Studyroom(request.getName(), request.getCapacity());
    }

    @GetMapping("/studyroom")
    public List<StudyroomResponse> getStudyroom(){
        List<StudyroomResponse> responses = new ArrayList<>();
        for(int i = 0; i < studyrooms.size() ; i++){
            responses.add(new StudyroomResponse(i + 1, studyrooms.get(i)));
        }
        return responses;
    }
}
