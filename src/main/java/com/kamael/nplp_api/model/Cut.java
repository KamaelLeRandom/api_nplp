package com.kamael.nplp_api.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="cut")
public class Cut {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Lob
    @Column(columnDefinition = "TEXT")
    private String searchLyric;
    
    @Lob
    @Column(columnDefinition = "TEXT")
    private String beforeLyric;
    
    @Lob
    @Column(columnDefinition = "TEXT")
    private String afterLyric;
    
    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;
    
    @ManyToOne
    @JoinColumn(name = "difficulty_id")
    private Difficulty difficulty;
    
    @OneToMany(mappedBy = "cut")
    private List<Daily> dailies;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchLyric() {
        return searchLyric;
    }

    public void setSearchLyric(String searchLyric) {
        this.searchLyric = searchLyric;
    }

    public String getBeforeLyric() {
        return beforeLyric;
    }

    public void setBeforeLyric(String beforeLyric) {
        this.beforeLyric = beforeLyric;
    }

    public String getAfterLyric() {
        return afterLyric;
    }

    public void setAfterLyric(String afterLyric) {
        this.afterLyric = afterLyric;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
    
    public Difficulty getDifficulty() {
    	return this.difficulty;
    }
    
    public void setDifficulty(Difficulty diff) {
    	this.difficulty = diff;
    }
    
    public List<Daily> getDailies() {
    	return this.dailies;
    }
    
    public void setDailies(List<Daily> dailies) {
    	this.dailies = dailies;
    }
}
