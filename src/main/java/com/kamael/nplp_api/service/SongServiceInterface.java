package com.kamael.nplp_api.service;

import java.util.List;

import com.kamael.nplp_api.model.Song;
import com.kamael.nplp_api.model.SongDTO;

public interface SongServiceInterface {
	SongDTO create(Song player);
	SongDTO read(Long id);
	List<SongDTO> readAll();
	SongDTO update(Long id, Song player);
	Boolean delete(Long id);
}
