package com.kamael.nplp_api.service;

import java.util.List;

import com.kamael.nplp_api.model.Result;
import com.kamael.nplp_api.model.ResultDTO;

public interface ResultServiceInterface {
	ResultDTO create(Result result);
	ResultDTO read(Long id);
	List<ResultDTO> readAll();
	ResultDTO update(Long id, Result result);
	Boolean delete(Long id);
}
