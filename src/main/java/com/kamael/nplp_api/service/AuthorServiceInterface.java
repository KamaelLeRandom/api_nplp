package com.kamael.nplp_api.service;

import java.util.List;

import com.kamael.nplp_api.model.Author;
import com.kamael.nplp_api.model.AuthorDTO;

public interface AuthorServiceInterface {
	AuthorDTO create(Author player);
	AuthorDTO read(Long id);
	List<AuthorDTO> readAll();
	AuthorDTO update(Long id, Author player);
	Boolean delete(Long id);
}
