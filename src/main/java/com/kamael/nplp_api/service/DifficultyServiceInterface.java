package com.kamael.nplp_api.service;

import java.util.List;

import com.kamael.nplp_api.model.Difficulty;
import com.kamael.nplp_api.model.DifficultyDTO;

public interface DifficultyServiceInterface {
	DifficultyDTO create(Difficulty player);
	DifficultyDTO read(Long id);
	List<DifficultyDTO> readAll();
	DifficultyDTO update(Long id, Difficulty player);
	Boolean delete(Long id);
}
