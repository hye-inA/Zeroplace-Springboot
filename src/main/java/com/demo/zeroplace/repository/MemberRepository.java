package com.demo.zeroplace.repository;

import com.demo.zeroplace.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByTel(String tel);
}
