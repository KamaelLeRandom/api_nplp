package com.kamael.nplp_api.model;

import java.util.List;
import java.util.stream.Collectors;

public class DifficultyDTO {
    private Long id;
    private String libelle;
    private Integer point;
    private List<CutDTO> cuts;

    public DifficultyDTO(Long id, String libelle, Integer point, List<CutDTO> cuts) {
    	this.id = id;
    	this.libelle = libelle;
    	this.point = point;
    	this.cuts = cuts;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public List<CutDTO> getCuts() {
        return cuts;
    }

    public void setCuts(List<CutDTO> cuts) {
        this.cuts = cuts;
    }
    
    public static DifficultyDTO convertToDTO(Difficulty difficulty) {
        if (difficulty == null)
            return null;

		List<CutDTO> cutDTOs = difficulty.getCuts().stream()
				.map(cut -> new CutDTO(cut.getId(), cut.getSearchLyric(), cut.getBeforeLyric(), cut.getAfterLyric(), null,null, null))
				.collect(Collectors.toList());
        
        return new DifficultyDTO(
        	difficulty.getId(),
        	difficulty.getLibelle(),
        	difficulty.getPoint(),
        	cutDTOs
        );
    }
}
