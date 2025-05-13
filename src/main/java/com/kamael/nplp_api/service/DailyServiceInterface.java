package com.kamael.nplp_api.service;

import java.util.List;

import com.kamael.nplp_api.model.Daily;
import com.kamael.nplp_api.model.DailyDTO;

public interface DailyServiceInterface {
	DailyDTO create(Daily daily);
	DailyDTO read(Long id);
	List<DailyDTO> readAll();
	DailyDTO update(Long id, Daily daily);
	Boolean delete(Long id);
}
