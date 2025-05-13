package com.kamael.nplp_api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorDTO {
    private Long id;
    private String lastname;
    private String nickname;
    private String firstname;
    private Date birthday;
    private List<SongDTO> songs;

    public AuthorDTO(Long id, String lastname, String nickname, String firstname, Date birthday, List<SongDTO> songs) {
        this.id = id;
        this.lastname = lastname;
        this.nickname = nickname;
        this.firstname = firstname;
        this.birthday = birthday;
        this.songs = songs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<SongDTO> getSongs() {
        return songs;
    }

    public void setSongs(List<SongDTO> songs) {
        this.songs = songs;
    }

    public static AuthorDTO convertToDTO(Author author) {
    	if (author == null)
    		return null;
    	
    	List<SongDTO> songDTOs = new ArrayList<SongDTO>();
    	
    	if (author.getSongs() != null) {
    		songDTOs = author.getSongs().stream()
    	            .map(song -> new SongDTO(song.getId(), song.getTitle(), song.getLyric(), song.getPublishAt(), song.getDuration(), null, null))
    	            .collect(Collectors.toList());
    	}

        return new AuthorDTO(
            author.getId(),
            author.getLastname(),
            author.getNickname(),
            author.getFirstname(),
            author.getBirthday(),
            songDTOs
        );
    }
}
