package com.demo.zeroplace.repository;

import com.demo.zeroplace.dto.response.MemberResponse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveMember(String name, String tel){
        String sql = "INSERT INTO user(name, tel) VALUES(?, ?)";
        jdbcTemplate.update(sql, name, tel);
    }

    public List<MemberResponse> getUserResponse() {
        String sql = "SELECT * FROM member"; // => findAll()
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new MemberResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("tel")
                )
        );
    }

    public boolean isUserNotExist(long id) {
        String sql = "SELECT * FROM member WHEHE id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 0, id).isEmpty();
    }

    public void updateMemberTel(String tel, long id){
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
