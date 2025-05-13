package com.kamael.nplp_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamael.nplp_api.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
