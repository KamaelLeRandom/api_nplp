package com.kamael.nplp_api.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kamael.nplp_api.model.Cut;
import com.kamael.nplp_api.model.Daily;
import com.kamael.nplp_api.model.DailyDTO;
import com.kamael.nplp_api.model.DifficultyDTO;
import com.kamael.nplp_api.model.Result;
import com.kamael.nplp_api.repository.CutRepository;
import com.kamael.nplp_api.repository.DailyRepository;

@Service
public class DailyService implements DailyServiceInterface {
	private final DailyRepository repository;
	private final CutRepository cutRepository;
	private final DifficultyService diffService;
	
	public DailyService(DailyRepository repository, CutRepository cutRepository, DifficultyService diffService) {
		this.repository = repository;
		this.diffService = diffService;
		this.cutRepository = cutRepository;
	}

	@Override
	public DailyDTO create(Daily daily) {
		try {
			return DailyDTO.convertToDTO(repository.save(daily));
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@Override
	public DailyDTO read(Long id) {
		try {
			Daily daily = repository.findById(id).orElse(null);
			return DailyDTO.convertToDTO(daily);
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
	
    public List<DailyDTO> readOrCreateToday() {
        LocalDate today = LocalDate.now();

        List<Daily> todaysDailies = repository.findByDate(today);

        if (todaysDailies == null || todaysDailies.isEmpty()) {
            todaysDailies = createDefaultDailiesForDate(today);
            repository.saveAll(todaysDailies);
        }

        return todaysDailies.stream()
	            .map(DailyDTO::convertToDTO)
	            .collect(Collectors.toList());
    }
    
    private List<Daily> createDefaultDailiesForDate(LocalDate date) {
        List<Daily> list = new ArrayList<Daily>();

        for (DifficultyDTO diff : diffService.readAll()) {
        	List<Cut> cuts = cutRepository.findByDifficulty_Id(diff.getId());
        	
        	if (cuts != null && !cuts.isEmpty()) {
                Daily d = new Daily();
                d.setResults(new ArrayList<Result>());
                d.setCreateFor(date);
                d.setCut(cuts.get(new Random().nextInt(cuts.size())));
                list.add(d);
        	}
        }

        return list;
    }

	@Override
	public List<DailyDTO> readAll() {
	    try {
	        return repository.findAll().stream()
	            .map(DailyDTO::convertToDTO)
	            .collect(Collectors.toList());
	    }
	    catch (Exception ex) {
	        System.out.println(ex.toString());
	        return null;
	    }
	}

	@Override
	public DailyDTO update(Long id, Daily daily) {
		try {
			return DailyDTO.convertToDTO(repository.findById(id).map(d -> {
				d.setCreateFor(daily.getCreateFor());
				d.setCut(daily.getCut());
				d.setResults(daily.getResults());
				return repository.save(d);
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
