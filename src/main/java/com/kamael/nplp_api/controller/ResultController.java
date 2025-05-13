package com.kamael.nplp_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamael.nplp_api.model.Player;
import com.kamael.nplp_api.model.Result;
import com.kamael.nplp_api.model.ResultDTO;
import com.kamael.nplp_api.repository.PlayerRepository;
import com.kamael.nplp_api.service.ResultService;

@RestController
@CrossOrigin
@RequestMapping("/result")
public class ResultController {
	private final ResultService service;
	private final PlayerRepository playerRepository;
	
	public ResultController(ResultService service, PlayerRepository playerRepository) {
		this.service = service;
		this.playerRepository = playerRepository;
	}
	
	@PostMapping("/create")
	public ResponseEntity<ResultDTO> create(@RequestBody Result result) {
	    try {
	        ResultDTO res = service.create(result);
	        if (res == null) 
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

	        Player player = playerRepository.findById(result.getPlayer().getId())
	                .orElseThrow(() -> new RuntimeException("Player not found"));
	        player.addPoints(result.getPoints());
	        playerRepository.save(player);

	        return ResponseEntity.ok(res);
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResultDTO> read(@PathVariable Long id) {
		try {
			ResultDTO res = service.read(id);
			if (res == null)
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<ResultDTO>> readAll() {
		try {
			List<ResultDTO> res = service.readAll();
			if (res == null)
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping("/player/{id}")
	public ResponseEntity<List<ResultDTO>> readAllFromPlayer(@PathVariable Long id) {
		try {
			List<ResultDTO> res = service.readByPlayer(id);
			if (res == null)
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
