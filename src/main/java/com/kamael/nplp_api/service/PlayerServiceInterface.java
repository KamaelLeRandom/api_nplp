package com.kamael.nplp_api.service;

import java.util.List;

import com.kamael.nplp_api.model.Player;

public interface PlayerServiceInterface {
	Player create(Player player);
	Player read(Long id);
	List<Player> readAll();
	Player update(Long id, Player player);
	Boolean delete(Long id);
}
