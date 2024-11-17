package com.demo.zeroplace.repository;

import com.demo.zeroplace.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {

    Optional<Session> findByAccessToken(String accessToken);
}
