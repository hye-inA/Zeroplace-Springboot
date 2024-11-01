package com.demo.zeroplace.repository;

import com.demo.zeroplace.dto.response.MemberResponse;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveMember(String name, String tel){
        String sql = "INSERT INTO user(name, tel) VALUES(?, ?)";
        jdbcTemplate.update(sql, name, tel);
    }

    public List<MemberResponse> getUserResponse() {
        String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new MemberResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("tel")
                )
        );
    }

    public boolean isUserNotExist(JdbcTemplate jdbcTemplate, long id) {
        String sql = "SELECT * FROM member WHEHE id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 0, id).isEmpty();
    }

    public void updateMemberTel(JdbcTemplate jdbcTemplate, String tel,long id){
        String sql = "UPDATE member SET tel = ? WHERE id = ?";
        jdbcTemplate.update(sql, tel, id);
    }

    public boolean isUserNotExist(String tel) {
        String sql = "SELECT * FROM member WHEHE tel = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 0, tel).isEmpty();
    }
    public void deleteMemberByTel(String tel){

        String sql = "DELETE FROM member WHERE tel = ?";
        jdbcTemplate.update(sql, tel);
    }
}
