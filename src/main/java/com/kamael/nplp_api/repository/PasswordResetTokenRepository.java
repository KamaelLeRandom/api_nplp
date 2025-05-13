package com.kamael.nplp_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamael.nplp_api.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
