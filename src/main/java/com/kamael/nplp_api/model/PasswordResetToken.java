package com.kamael.nplp_api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String token;
    
    @Column
    private LocalDateTime expiryDate;

    @OneToOne
    private Player player;

    public Long getId() {
    	return this.id;
    }
    
    public String getToken() {
    	return this.token;
    }
    
    public void setToken(String token) {
    	this.token = token;
    }
    
    public LocalDateTime getExpiryDate() {
    	return this.expiryDate;
    }
    
    public void setExpiryDate(LocalDateTime expiryDate) {
    	this.expiryDate = expiryDate;
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    public void setPlayer(Player player) {
    	this.player = player;
    }
}
