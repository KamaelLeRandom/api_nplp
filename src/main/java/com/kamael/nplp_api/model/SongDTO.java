package com.kamael.nplp_api.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SongDTO {
    private Long id;
    private String title;
    private String lyric;
    private Date publishAt;
    private Double duration;
    private List<AuthorDTO> authors;
    private List<CutDTO> cuts;

    public SongDTO(Long id, String title, String lyric, Date publishAt, Double duration, List<AuthorDTO> authors, List<CutDTO> cuts) {
        this.id = id;
        this.title = title;
        this.lyric = lyric;
        this.publishAt = publishAt;
        this.duration = duration;
        this.authors = authors;
        this.cuts = cuts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public Date getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(Date publishAt) {
        this.publishAt = publishAt;
    }
    
	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }
    
    public List<CutDTO> getCuts() {
    	return cuts;
    }
    
    public void setCuts(List<CutDTO> cuts) {
    	this.cuts = cuts;
    }

    public static SongDTO convertToDTO(Song song) {
    	if (song == null)
    		return null;
    	
        List<AuthorDTO> authorDTOs = song.getAuthors().stream()
            .map(author -> new AuthorDTO(author.getId(), author.getLastname(), author.getNickname(), author.getFirstname(), author.getBirthday(), null))
            .collect(Collectors.toList());
        
        List<CutDTO> cutsDTOs = song.getCuts().stream()
                .map(cut -> new CutDTO(
                		cut.getId(), 
                		cut.getSearchLyric(),
                		cut.getBeforeLyric(), 
                		cut.getAfterLyric(), 
                		null, 
                		new DifficultyDTO(
                				cut.getDifficulty().getId(), 
                				cut.getDifficulty().getLibelle(), 
                				cut.getDifficulty().getPoint(), 
                				null
                		),
                		null))
                .collect(Collectors.toList());
                
        return new SongDTO(
            song.getId(),
            song.getTitle(),
            song.getLyric(),
            song.getPublishAt(),
            song.getDuration(),
            authorDTOs,
            cutsDTOs
        );
    }
}
