package com.kamael.nplp_api.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="song")
public class Song {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column
    private Date publishAt;

    @Column(length=100)
    private String title;
    
    @Lob
    @Column(columnDefinition = "TEXT")
    private String lyric;
   
    @Column
    private Double duration;

    @ManyToMany
    @JoinTable(
        name = "song_author", 
        joinColumns = @JoinColumn(name = "song_id"), 
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;
    
    @OneToMany(mappedBy = "song")
    private List<Cut> cuts;

    // Getters et setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPublishAt() {
        return this.publishAt;
    }

    public void setPublishAt(Date publishAt) {
        this.publishAt = publishAt;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLyric() {
        return this.lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }
    
    public Double getDuration() {
    	return this.duration;
    }
    
    public void setDuration(Double duration) {
    	this.duration = duration;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    
    public List<Cut> getCuts() {
        return cuts;
    }

    public void setCuts(List<Cut> cuts) {
        this.cuts = cuts;
    }
}
