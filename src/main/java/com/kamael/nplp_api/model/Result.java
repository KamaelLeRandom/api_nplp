package com.kamael.nplp_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="result")
public class Result {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private Integer points;
    
    @Column
    private Integer numberOfTry;
    
    @Column
    private Boolean useHint;
    
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "daily_id")
    private Daily daily;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getNumberOfTry() {
        return numberOfTry;
    }

    public void setNumberOfTry(Integer numberOfTry) {
        this.numberOfTry = numberOfTry;
    }

    public Boolean getUseHint() {
        return useHint;
    }

    public void setUseHint(Boolean useHint) {
        this.useHint = useHint;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

}
