package com.kamael.nplp_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamael.nplp_api.model.Player;
import com.kamael.nplp_api.service.PlayerService;

@RestController
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }
    
	@PostMapping("/create")
	public Player create(@RequestBody Player player) {
		return service.create(player);
	}
	
	@GetMapping("/{id}")
	public Player read(@PathVariable Long id) {
		return service.read(id);
	}
	
	@GetMapping
	public List<Player> readAll() {
		return service.readAll();
	}
	
	@PutMapping("/update/{id}")
	public Player update(@PathVariable Long id, @RequestBody Player player) {
		return service.update(id, player);
	}
	
	@DeleteMapping("/delete/{id}")
	public Boolean delete(@PathVariable Long id) {
		return service.delete(id);
	}
}
