package com.kamael.nplp_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kamael.nplp_api.model.Difficulty;
import com.kamael.nplp_api.model.DifficultyDTO;
import com.kamael.nplp_api.repository.DifficultyRepository;

@Service
public class DifficultyService implements DifficultyServiceInterface {
	private DifficultyRepository repository;
	
	public DifficultyService(DifficultyRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public DifficultyDTO create(Difficulty diff) {
		try {
			return DifficultyDTO.convertToDTO(repository.save(diff));
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@Override
	public DifficultyDTO read(Long id) {
		try {
			Difficulty diff = repository.findById(id).orElse(null);
			return DifficultyDTO.convertToDTO(diff);
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@Override
	public List<DifficultyDTO> readAll() {
	    try {
	        return repository.findAll().stream()
	            .map(DifficultyDTO::convertToDTO)
	            .collect(Collectors.toList());
	    }
	    catch (Exception ex) {
	        System.out.println(ex.toString());
	        return null;
	    }
	}

	@Override
	public DifficultyDTO update(Long id, Difficulty diff) {
		try {
			return DifficultyDTO.convertToDTO(repository.findById(id).map(d -> {
				d.setLibelle(d.getLibelle());
				d.setPoint(d.getPoint());
				return repository.save(d);
			}).orElseGet(null)) ;
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
