package com.kamael.nplp_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kamael.nplp_api.model.Author;
import com.kamael.nplp_api.model.AuthorDTO;
import com.kamael.nplp_api.repository.AuthorRepository;

@Service
public class AuthorService implements AuthorServiceInterface {
	private AuthorRepository repository;
	
	public AuthorService(AuthorRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public AuthorDTO create(Author author) {
		try {
			return AuthorDTO.convertToDTO(repository.save(author));
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@Override
	public AuthorDTO read(Long id) {
		try {
			return AuthorDTO.convertToDTO(repository.findById(id).orElseGet(null));
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@Override
	public List<AuthorDTO> readAll() {
	    try {
	        return repository.findAll().stream()
	            .map(AuthorDTO::convertToDTO)
	            .collect(Collectors.toList());
	    }
	    catch (Exception ex) {
	        System.out.println(ex.toString());
	        return null;
	    }
	}


	@Override
	public AuthorDTO update(Long id, Author author) {
		try {
			return AuthorDTO.convertToDTO(repository.findById(id).map(p -> {
				p.setNickname(author.getNickname());
				p.setLastname(author.getLastname());
				p.setFirstname(author.getFirstname());
				p.setBirthday(author.getBirthday());
				p.setSongs(author.getSongs());
				return repository.save(p);
			}).orElseGet(null));
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@Override
	public Boolean delete(Long id) {
		try {
			repository.deleteById(id);
			return true;
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return false;
		}
	}

}
