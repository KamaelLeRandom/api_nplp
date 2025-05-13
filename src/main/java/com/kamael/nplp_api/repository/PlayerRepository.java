package com.kamael.nplp_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kamael.nplp_api.model.Player;
import com.kamael.nplp_api.model.PlayerDTO;

public interface PlayerRepository extends JpaRepository<Player, Long> {
	Player findByName(String name);
	Player findByEmail(String email);

	@Query("SELECT p FROM Player p WHERE p.name LIKE :name")
	Player findDTOByName(String name);
}
