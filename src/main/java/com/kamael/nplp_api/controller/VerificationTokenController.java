package com.kamael.nplp_api.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kamael.nplp_api.model.Player;
import com.kamael.nplp_api.model.VerificationToken;
import com.kamael.nplp_api.repository.PlayerRepository;
import com.kamael.nplp_api.repository.VerificationTokenRepository;

@RestController
@RequestMapping("/verify")
public class VerificationTokenController {
    private final VerificationTokenRepository tokenRepository;
    private final PlayerRepository playerRepository;
    
    public VerificationTokenController(VerificationTokenRepository vRepository, PlayerRepository pRepository) {
    	this.tokenRepository = vRepository;
    	this.playerRepository = pRepository;
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUser(@RequestParam("token") String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null)
            return ResponseEntity.badRequest().body("Token invalide.");

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now()))
            return ResponseEntity.badRequest().body("Le lien a expiré.");

        Player player = verificationToken.getPlayer();
        player.setIsConfirmed(true);
        playerRepository.save(player);

        return ResponseEntity.ok("Compte confirmé avec succès !");
    }
}
