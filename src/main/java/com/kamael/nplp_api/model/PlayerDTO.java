package com.kamael.nplp_api.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerDTO {
	private Long id;
	private String name;
	private String email;
	private String role;
	private Integer points;
	private Date createAt;
	private Date lastEditAt;
	private Boolean isConfirmed;
	private List<ResultDTO> result;
	
	public PlayerDTO(Player player) {
        this.setId(player.getId());
        this.setName(player.getName());
        this.setEmail(player.getEmail());
        this.setRole(player.getRole());
        this.setPoints(player.getPoints());
        this.setCreateAt(player.getCreateAt());
        this.setLastEditAt(player.getLastEditAt());
        this.setIsConfirmed(player.getIsConfirmed());
	}
	
	public PlayerDTO(Long id, String name, String email, String role, Integer points, Date createAt, Date lastEditAt, Boolean isConfirmed, List<ResultDTO> results) {
	    this.id = id;
	    this.name = name;
	    this.email = email;
	    this.role = role;
	    this.points = points;
	    this.createAt = createAt;
	    this.lastEditAt = lastEditAt;
	    this.result = results;
	    this.isConfirmed = isConfirmed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPoints() {
		return points;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getLastEditAt() {
		return lastEditAt;
	}

	public void setLastEditAt(Date lastEditAt) {
		this.lastEditAt = lastEditAt;
	}
	
	public Boolean getIsConfirmed() {
		return this.isConfirmed;
	}

	public void setIsConfirmed(Boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	
	public List<ResultDTO> getResults() {
		return this.result;
	}
	
	public void setResults(List<ResultDTO> results) {
		this.result = results;
	}
	
    public static PlayerDTO convertToDTO(Player player) {
        if (player == null)
            return null;
        
        return new PlayerDTO(
        	player.getId(),
        	player.getName(),
        	player.getEmail(),
        	player.getRole(),
        	player.getPoints(),
        	player.getCreateAt(),
        	player.getLastEditAt(),
        	player.getIsConfirmed(),
        	convertResultDTOForPlayer(player.getResult())
        );
    }
    
    public static List<ResultDTO> convertResultDTOForPlayer(List<Result> results) {
        return results.stream()
                .map(result -> {
                    Daily daily = result.getDaily();

                    return new ResultDTO(
                        result.getId(),
                        result.getPoints(),
                        result.getNumberOfTry(),
                        result.getUseHint(),
                        null,
                        new DailyDTO(
                        	daily.getId(),
                        	daily.getCreateFor(),
                        	null,
                        	null
                        )
                    );
                })
                .collect(Collectors.toList());
    }
}
