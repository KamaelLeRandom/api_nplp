package com.kamael.nplp_api.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamael.nplp_api.configuration.JwtUtils;
import com.kamael.nplp_api.model.Player;
import com.kamael.nplp_api.model.PlayerDTO;
import com.kamael.nplp_api.service.PlayerService;

@RestController
@CrossOrigin
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService service;
    private final JwtUtils jwtUtils;
    
    public PlayerController(PlayerService service, JwtUtils jwtUtils) {
        this.service = service;
        this.jwtUtils = jwtUtils;
    }
    
	@PostMapping("/create")
	public Player create(@RequestBody Player player) {
		return service.create(player);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Player> read(@PathVariable Long id) {
	    try {
	        Player player = service.read(id);
	        return ResponseEntity.ok(player);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}
	
	@GetMapping
	public List<Player> readAll() {
		return service.readAll();
	}
	
	@GetMapping("/leaderboard")
	public ResponseEntity<List<PlayerDTO>> read() {
	    try {
	    	List<Player> players = service.readAll();
	    	
	        List<PlayerDTO> topPlayerDTOs = players.stream()
	                .sorted((p1, p2) -> Integer.compare(p2.getPoints(), p1.getPoints()))
	                .limit(10)
	                .map(PlayerDTO::convertToDTO)
	                .collect(Collectors.toList());
	    	
	        return ResponseEntity.ok(topPlayerDTOs);
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	
	@PutMapping("/update/{id}")
	public Player update(@PathVariable Long id, @RequestBody Player player) {
		return service.update(id, player) ;
	}
	
	@DeleteMapping("/delete/{id}")
	public Boolean delete(@PathVariable Long id) {
		return service.delete(id);
	}
	
    @GetMapping("/me")
    public ResponseEntity<?> getUserFromToken(@RequestHeader("Authorization") String authHeader) {
    	try {
            if (authHeader == null || !authHeader.startsWith("Bearer "))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalid or missing");
            
            String token = authHeader.substring(7);
            String username = jwtUtils.extractUsername(token);
            Date expiration = jwtUtils.extractExpirationDate(token);
            Date now = new Date();
            
            if (expiration.before(now))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalid or missing");
            
            Player player = service.readDTOByName(username);
            
            return ResponseEntity.ok(PlayerDTO.convertToDTO(player));
    	}
    	catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.toString());
    	}
    }
}
