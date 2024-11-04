package com.demo.zeroplace.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import com.demo.zeroplace.dto.request.StudyroomCreateRequest;
import com.demo.zeroplace.dto.response.StudyroomResponse;
import com.demo.zeroplace.repository.StudyroomRepository;

@Service
public class StudyroomService {

    private final StudyroomRepository studyroomRepository;

    public StudyroomService(JdbcTemplate jdbcTemplate) {
        this.studyroomRepository = new StudyroomRepository(jdbcTemplate);
    }

    public void saveStudyroom(StudyroomCreateRequest request){
        studyroomRepository.saveStudyroom(request.getName(),request.getCapacity());
    }
    public List<StudyroomResponse> getStudyroom(){
        return studyroomRepository.getStudyroomResponse();
    }
}
