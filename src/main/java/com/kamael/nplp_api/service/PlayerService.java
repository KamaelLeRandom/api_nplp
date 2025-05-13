package com.kamael.nplp_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kamael.nplp_api.model.Player;
import com.kamael.nplp_api.model.PlayerDTO;
import com.kamael.nplp_api.repository.PlayerRepository;

@Service
public class PlayerService implements PlayerServiceInterface {
	private PlayerRepository repository;
	
	public PlayerService(PlayerRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Player create(Player player) {
		return repository.save(player);
	}

	@Override
	public Player read(Long id) {
	    return repository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Player not found"));
	}

	@Override
	public List<Player> readAll() {
		return repository.findAll();
	}
	
	public Player readDTOByName(String username) {
		return repository.findDTOByName(username);
	}

	@Override
	public Player update(Long id, Player player) {
		return repository.findById(id).map(p -> {
			p.setName(player.getName());
			return repository.save(p);
		}).orElseThrow(() -> new RuntimeException("Player not find"));
	}

	@Override
	public Boolean delete(Long id) {
		repository.deleteById(id);
		return true;
	}

}
