package com.demo.zeroplace.controller;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.zeroplace.dto.request.StudyroomCreateRequest;
import com.demo.zeroplace.dto.response.StudyroomResponse;
import com.demo.zeroplace.service.StudyroomService;

@RestController
public class StudycafeController {

    private final JdbcTemplate jdbcTemplate;
    private final StudyroomService studyroomService;

    public StudycafeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.studyroomService = new StudyroomService(jdbcTemplate);
    }

    @PostMapping("/studyroom")
    public void saveStudyroom(@RequestBody StudyroomCreateRequest request) {
       studyroomService.saveStudyroom(request);
    }

    @GetMapping("/studyroom")
    public List<StudyroomResponse> getStudyroom() {
        return studyroomService.getStudyroom();
    }
}
