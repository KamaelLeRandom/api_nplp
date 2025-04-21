package com.kamael.nplp_api.service;

import org.springframework.stereotype.Service;

import com.kamael.nplp_api.model.Player;
import com.kamael.nplp_api.repository.PlayerRepository;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class AuthService implements UserDetailsService {
	private PlayerRepository playerRepository;

	public AuthService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Player player = playerRepository.findByName(username);
		
		if (player == null) {
			throw new UsernameNotFoundException("Player not found with username: " + username);
		}
		
		return new User(username, player.getPassword(), 
				Collections.singletonList(new SimpleGrantedAuthority(player.getRole())));
	}
}
