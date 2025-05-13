package com.kamael.nplp_api.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;

@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String name;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(length = 50)
    private String role;

    @Column
    private Integer points;

    @Column
    private Date createAt;

    @Column
    private Date lastEditAt;
    
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isConfirmed;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Result> results;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getPoints() {
        return points;
    }
    
    public void addPoints(Integer value) {
    	this.points += value;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getLastEditAt() {
        return lastEditAt;
    }

    public void setLastEditAt(Date lastEditAt) {
        this.lastEditAt = lastEditAt;
    }

    public List<Result> getResult() {
        return results;
    }

    public void setResults(List<Result> result) {
        this.results = result;
    }
    
    public Boolean getIsConfirmed() {
    	return this.isConfirmed;
    }
    
    public void setIsConfirmed(Boolean isConfirmed) {
    	this.isConfirmed = isConfirmed;
    }
}
