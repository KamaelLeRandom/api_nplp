package com.kamael.nplp_api.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kamael.nplp_api.configuration.JwtUtils;
import com.kamael.nplp_api.model.PasswordResetToken;
import com.kamael.nplp_api.model.Player;
import com.kamael.nplp_api.model.PlayerDTO;
import com.kamael.nplp_api.model.VerificationToken;
import com.kamael.nplp_api.repository.PasswordResetTokenRepository;
import com.kamael.nplp_api.repository.PlayerRepository;
import com.kamael.nplp_api.repository.VerificationTokenRepository;
import com.kamael.nplp_api.service.EmailService;
import com.kamael.nplp_api.service.PlayerService;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
	private final PlayerService service;
    private final PlayerRepository repository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authentificationManager;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;

    public AuthController(PlayerService playerService, PlayerRepository repository, VerificationTokenRepository vRepository, PasswordResetTokenRepository passwordResetTokenRepository, PasswordEncoder passwordEncoder, AuthenticationManager authentificationManager, JwtUtils jwtUtils, EmailService emailService) {
    	this.service = playerService;
    	this.repository = repository;
    	this.verificationTokenRepository = vRepository;
    	this.passwordResetTokenRepository = passwordResetTokenRepository;
    	this.passwordEncoder = passwordEncoder;
    	this.authentificationManager = authentificationManager;
    	this.jwtUtils = jwtUtils;
    	this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Player player) {
    	if (repository.findByName(player.getName()) != null) {
    		return ResponseEntity.badRequest().body("Username is already in use");
    	}
    	
    	player.setPassword(passwordEncoder.encode(player.getPassword()));
    	player.setCreateAt(new Date());
    	player.setLastEditAt(new Date());
    	player.setPoints(0);
    	player.setRole("ROLE_USER");
    	player.setIsConfirmed(false);
    	
    	Player playerSave = repository.save(player);
    	
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setPlayer(player);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        verificationTokenRepository.save(verificationToken);

        String confirmationUrl = "http://localhost:8080/verify/confirm?token=" + token;

        String subject = "Confirmation de votre compte";
        String body = "Bonjour " + player.getName() + ",\n\n" +
                      "Veuillez cliquer sur ce lien pour confirmer votre compte :\n" +
                      confirmationUrl + "\n\n" +
                      "Ce lien expire dans 24 heures.";
        
        emailService.sendConfirmationEmail(player.getEmail(), subject, body);
    	
    	return ResponseEntity.ok(playerSave);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Player player) {
    	try {
    		if (player.getName() == null || player.getPassword() == null || player.getName().isEmpty() || player.getPassword().isEmpty()) {
    		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password must not be empty");
    		}
    		
    		Authentication authentification = authentificationManager.authenticate(new UsernamePasswordAuthenticationToken(player.getName(), player.getPassword()));
    		if (authentification.isAuthenticated()) {
                Player authenticatedPlayer = service.readDTOByName(player.getName());

    			Map<String, Object> authData = new HashMap<String, Object>();
    			authData.put("id", authenticatedPlayer.getId());
    			authData.put("name", authenticatedPlayer.getName());
    			authData.put("email", authenticatedPlayer.getEmail());
    			authData.put("createAt", authenticatedPlayer.getCreateAt());
    			authData.put("lastEditAt", authenticatedPlayer.getLastEditAt());
    			authData.put("points", authenticatedPlayer.getPoints());
    			authData.put("role", authenticatedPlayer.getRole());
    			authData.put("isConfirmed", authenticatedPlayer.getIsConfirmed());
    			authData.put("results", PlayerDTO.convertResultDTOForPlayer(authenticatedPlayer.getResult()));
    			authData.put("token", jwtUtils.generateToken(player.getName()));
    			return ResponseEntity.ok(authData);
    		}
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    	} catch (AuthenticationException e) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    	}
    }
    

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        String message = "Si cet email est enregistré, un lien de réinitialisation a été envoyé.";
        String email = request.get("email");
        Player player = repository.findByEmail(email);
        if (player == null)
            return ResponseEntity.ok(Collections.singletonMap("message", message));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setPlayer(player);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        passwordResetTokenRepository.save(resetToken);

        String resetLink = "http://localhost:8080/auth/reset-password?token=" + token;

        emailService.sendConfirmationEmail(
        	player.getEmail(),
            "Réinitialisation de mot de passe",
            "Cliquez ici pour réinitialiser votre mot de passe : " + resetLink
        );

        return ResponseEntity.ok(Collections.singletonMap("message", message)); 
    }
    
    @GetMapping("/reset-password")
    public ResponseEntity<Void> verifyResetToken(@RequestParam String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);

        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String redirectUrl = "http://localhost:4200/password-reset?token=" + resetToken.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> payload) {
    	String message = "Erreur durant la réinitialisation.";
        String token = payload.get("token");
        String newPassword = payload.get("newPassword");

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", message));
        }

        Player player = resetToken.getPlayer();
        player.setPassword(passwordEncoder.encode(newPassword));
        repository.save(player);

        passwordResetTokenRepository.delete(resetToken);
        
        message = "Mot de passe mis à jour avec succès.";
        return ResponseEntity.ok(Collections.singletonMap("message", message));
    }
}
