package com.demo.zeroplace.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.demo.zeroplace.domain.member.Member;
import com.demo.zeroplace.dto.request.MemberCreateRequest;
import com.demo.zeroplace.dto.request.MemberUpdateRequest;
import com.demo.zeroplace.dto.response.MemberResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import static org.apache.logging.log4j.ThreadContext.isEmpty;


@RestController
public class MemberController {
    private final JdbcTemplate jdbcTemplate;

    public MemberController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/member")
    public void saveMember(@RequestBody MemberCreateRequest request) {
        String sql = "INSERT INTO member(name, tel) VALUES(?, ?)";
        jdbcTemplate.update(sql, request.getName(), request.getTel());
    }

    @GetMapping("/member")
    public List<MemberResponse> getMembers() {
        String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new MemberResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("tel")
                )
        );
    }

    // TODO : 사용자 존재 여부 확인 쿼리 구체화, 구체적인 예외 클래스 구현
    @PutMapping("/member")
    public void updateMember(@RequestBody MemberUpdateRequest request) {
        String readSql = "SELECT * FROM user WHEHE id = ?";
        boolean isUserNotExist = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, request.getId()).isEmpty();
        if (isUserNotExist) {
            throw new IllegalArgumentException();
        }

        String updateSql = "UPDATE member SET tel = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, request.getTel(),request.getId());
    }

    //쿼리 파라미터가 1개라서 객체를 사용하지 않고 @RequestParam 사용
    // TODO : 사용자의 이름도 변경될 가능성 있는 데이터임으로 DTO로 변경
    @DeleteMapping("/member")
    public void deleteMember(@RequestParam String tel) {
        String readSql = "SELECT * FROM user WHEHE tel = ?";
        boolean isUserNotExist = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, tel).isEmpty();
        if (isUserNotExist) {
            throw new IllegalArgumentException();
        }
        String deleteSql = "DELETE FROM member WHERE tel = ?";
        jdbcTemplate.update(deleteSql, tel);
    }

}
