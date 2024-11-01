package com.demo.zeroplace.repository;


import com.demo.zeroplace.dto.response.StudyroomResponse;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


public class StudyroomRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudyroomRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveStudyroom(String name, Integer capacity){
        String sql = "INSERT INTO studyroom(name, capacity) VALUES(?, ?)";
        jdbcTemplate.update(sql, name, capacity);
    }

    public List<StudyroomResponse> getStudyroomResponse() {
        String sql = "SELECT * from studyroom";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new StudyroomResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("capacity")
                )
        );
    }

}
