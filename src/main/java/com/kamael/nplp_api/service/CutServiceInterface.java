package com.kamael.nplp_api.service;

import java.util.List;

import com.kamael.nplp_api.model.Cut;
import com.kamael.nplp_api.model.CutDTO;

public interface CutServiceInterface {
	CutDTO create(Cut player);
	CutDTO read(Long id);
	List<CutDTO> readAll();
	CutDTO update(Long id, Cut player);
	Boolean delete(Long id);
}
