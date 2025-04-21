package com.kamael.nplp_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamael.nplp_api.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
	Player findByName(String name);
}
