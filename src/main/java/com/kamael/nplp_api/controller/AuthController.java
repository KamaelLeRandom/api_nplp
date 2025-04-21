package com.kamael.nplp_api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamael.nplp_api.configuration.JwtUtils;
import com.kamael.nplp_api.model.Player;
import com.kamael.nplp_api.repository.PlayerRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PlayerRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authentificationManager;
    private final JwtUtils jwtUtils;

    public AuthController(PlayerRepository repository, PasswordEncoder passwordEncoder, AuthenticationManager authentificationManager, JwtUtils jwtUtils) {
    	this.repository = repository;
    	this.passwordEncoder = passwordEncoder;
    	this.authentificationManager = authentificationManager;
    	this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Player player) {
    	if (repository.findByName(player.getName()) != null) {
    		return ResponseEntity.badRequest().body("Username is already in use");
    	}
    	player.setPassword(passwordEncoder.encode(player.getPassword()));
    	return ResponseEntity.ok(repository.save(player));
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Player player) {
    	try {
    		Authentication authentification = authentificationManager.authenticate(new UsernamePasswordAuthenticationToken(player.getName(), player.getPassword()));
    		if (authentification.isAuthenticated()) {
    			Map<String, Object> authData = new HashMap<String, Object>();
    			authData.put("token", jwtUtils.generateToken(player.getName()));
    			authData.put("type", "Bearer");
    			return ResponseEntity.ok(authData);
    		}
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    	} catch (AuthenticationException e) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    	}
    }
}
