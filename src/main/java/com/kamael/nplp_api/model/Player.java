package com.kamael.nplp_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Player")
public class Player {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	
	@Column(length=50)
	private String name;
	
	public String getName() {
		return this.name;	
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Column(length=100)
	private String email;
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(length=100)
	private String password;
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(length=50)
	private String role;
	
	public String getRole() {
		return this.role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}
