package com.demo.zeroplace.repository;

import com.demo.zeroplace.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByName(String name);

    Optional<Member> findByEmailAndPassword(String email, String password);

}
