package com.kamael.nplp_api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CutDTO {
    private Long id;
    private String searchLyric;
    private String beforeLyric;
    private String afterLyric;
    
    private SongDTO song;
    private DifficultyDTO difficulty;
    private List<DailyDTO> dailies;

    public CutDTO(Long id, String searchLyric, String beforeLyric, String afterLyric, SongDTO song, DifficultyDTO difficulty, List<DailyDTO> dailies) {
        this.id = id;
        this.searchLyric = searchLyric;
        this.beforeLyric = beforeLyric;
        this.afterLyric = afterLyric;
        this.song = song;
        this.difficulty = difficulty;
        this.dailies = dailies;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchLyric() {
        return this.searchLyric;
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

    public SongDTO getSong() {
        return song;
    }

    public void setSong(SongDTO song) {
        this.song = song;
    }

    public DifficultyDTO getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyDTO difficulty) {
        this.difficulty = difficulty;
    }

    public List<DailyDTO> getDailies() {
        return dailies;
    }

    public void setDailies(List<DailyDTO> dailies) {
        this.dailies = dailies;
    }
    
    public static CutDTO convertToDTO(Cut cut) {
        if (cut == null)
            return null;
        
        Song song = cut.getSong();
        
        List<AuthorDTO> authorDTOs = new ArrayList<>();
        if (song.getAuthors() != null && !song.getAuthors().isEmpty()) {
            authorDTOs = song.getAuthors().stream()
                .map(author -> new AuthorDTO(author.getId(), author.getLastname(), author.getNickname(), author.getFirstname(), null, null))
                .collect(Collectors.toList());
        }
        
        SongDTO songDTO = new SongDTO(song.getId(), song.getTitle(), null, song.getPublishAt(), song.getDuration(), authorDTOs, null);
        
        Difficulty difficulty = cut.getDifficulty();
        DifficultyDTO difficultyDTO = new DifficultyDTO(difficulty.getId(), difficulty.getLibelle(), difficulty.getPoint(), null);        
        
        List<DailyDTO> dailyDTOs = new ArrayList<DailyDTO>();
        if (!cut.getDailies().isEmpty()) {
        	dailyDTOs = cut.getDailies().stream()
                    .map(daily -> new DailyDTO(daily.getId(), daily.getCreateFor(), null, null))
                    .collect(Collectors.toList());
        }

        return new CutDTO(
        	cut.getId(),
	        cut.getSearchLyric(),
            cut.getBeforeLyric(),
            cut.getAfterLyric(),
            songDTO,
            difficultyDTO,
            dailyDTOs
       	);
    }
}
;