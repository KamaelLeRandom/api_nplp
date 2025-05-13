package com.kamael.nplp_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kamael.nplp_api.model.Cut;
import com.kamael.nplp_api.model.CutDTO;
import com.kamael.nplp_api.repository.CutRepository;

@Service
public class CutService implements CutServiceInterface {
	private final CutRepository repository;
	
	public CutService(CutRepository repository) {
		this.repository = repository;
	}

	@Override
	public CutDTO create(Cut cut) {
		try {
			return CutDTO.convertToDTO(repository.save(cut));
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@Override
	public CutDTO read(Long id) {
		try {
			Cut cut = repository.findById(id).orElse(null);
			return CutDTO.convertToDTO(cut);
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@Override
	public List<CutDTO> readAll() {
	    try {
	        return repository.findAll().stream()
	            .map(CutDTO::convertToDTO)
	            .collect(Collectors.toList());
	    }
	    catch (Exception ex) {
	        System.out.println(ex.toString());
	        return null;
	    }
	}
	
	public List<CutDTO> readByDifficulty(Long difficultyId) {
	    return repository.findByDifficulty_Id(difficultyId).stream()
	        .map(CutDTO::convertToDTO)
	        .collect(Collectors.toList());
	}

	@Override
	public CutDTO update(Long id, Cut cut) {
		try {
			return CutDTO.convertToDTO(repository.findById(id).map(c -> {
				c.setSearchLyric(cut.getSearchLyric());
				c.setBeforeLyric(cut.getBeforeLyric());
				c.setAfterLyric(cut.getAfterLyric());
				c.setDifficulty(cut.getDifficulty());
				c.setDailies(cut.getDailies());
				c.setSong(c.getSong());
				return repository.save(c);
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
