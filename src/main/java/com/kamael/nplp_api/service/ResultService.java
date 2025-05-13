package com.kamael.nplp_api.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kamael.nplp_api.model.Result;
import com.kamael.nplp_api.model.ResultDTO;
import com.kamael.nplp_api.repository.ResultRepository;

@Service
public class ResultService implements ResultServiceInterface {
	private ResultRepository repository;
	
	public ResultService(ResultRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResultDTO create(Result result) {
		try {
			return ResultDTO.convertToDTO(repository.save(result));
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@Override
	public ResultDTO read(Long id) {
		try {
			return ResultDTO.convertToDTO(repository.findById(id).orElseGet(null));
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
	
	public List<ResultDTO> readByPlayer(Long playerId) {
        List<Result> results = repository.findByPlayerId(playerId);

        if (results == null || results.isEmpty())
            return Collections.emptyList();

        return results.stream()
                .map(result -> ResultDTO.convertToDTO(result))
                .collect(Collectors.toList());	
    }

	@Override
	public List<ResultDTO> readAll() {
	    try {
	        return repository.findAll().stream()
	            .map(ResultDTO::convertToDTO)
	            .collect(Collectors.toList());
	    }
	    catch (Exception ex) {
	        System.out.println(ex.toString());
	        return null;
	    }
	}

	@Override
	public ResultDTO update(Long id, Result result) {
		try {
			return ResultDTO.convertToDTO(repository.findById(id).map(r -> {
				r.setDaily(result.getDaily());
				r.setNumberOfTry(result.getNumberOfTry());
				r.setPlayer(result.getPlayer());
				r.setPoints(result.getPoints());
				r.setUseHint(result.getUseHint());
				return repository.save(r);
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
