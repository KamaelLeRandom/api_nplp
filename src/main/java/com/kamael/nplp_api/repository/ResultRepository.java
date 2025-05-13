package com.kamael.nplp_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamael.nplp_api.model.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {
	List<Result> findByPlayerId(Long playerId);
}
