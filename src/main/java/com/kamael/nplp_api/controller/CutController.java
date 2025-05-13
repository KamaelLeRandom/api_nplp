package com.kamael.nplp_api.controller;

import java.util.List;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamael.nplp_api.model.Cut;
import com.kamael.nplp_api.model.CutDTO;
import com.kamael.nplp_api.service.CutService;

@RestController
@CrossOrigin
@RequestMapping("/cut")
public class CutController {
	private final CutService service;
	
	public CutController(CutService service) {
		this.service = service;
	}
	
	@PostMapping("/create")
	public ResponseEntity<CutDTO> create(@RequestBody Cut cut) {
		try {
			CutDTO res = service.create(cut);
			if (res == null)
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CutDTO> read(@PathVariable Long id) {
		try {
			CutDTO cut = service.read(id);
			if (cut == null)
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        return ResponseEntity.ok(cut);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping("/random")
	public ResponseEntity<CutDTO> readRandom() {
	    try {
	        List<CutDTO> allCuts = service.readAll();

	        if (allCuts == null || allCuts.isEmpty())
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

	        Random random = new Random();
	        CutDTO randomCut = allCuts.get(random.nextInt(allCuts.size()));

	        return ResponseEntity.ok(randomCut);
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	@GetMapping("/difficulty/{difficultyId}")
	public ResponseEntity<CutDTO> readRandomDifficulty(@PathVariable Long difficultyId) {
	    try {
	        List<CutDTO> cutsByDifficulty = service.readByDifficulty(difficultyId);

	        if (cutsByDifficulty == null || cutsByDifficulty.isEmpty())
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

	        // Sélectionne un Cut aléatoire
	        Random random = new Random();
	        CutDTO randomCut = cutsByDifficulty.get(random.nextInt(cutsByDifficulty.size()));

	        return ResponseEntity.ok(randomCut);
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	@GetMapping
	public ResponseEntity<List<CutDTO>> readAll() {
		try {
			List<CutDTO> res = service.readAll();
			if (res == null)
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<CutDTO> update(@PathVariable Long id, @RequestBody Cut cut) {
		try {
			CutDTO cutDTO = service.update(id, cut);
			if (cutDTO == null)
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        return ResponseEntity.ok(cutDTO);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {
		try {
			Boolean res = service.delete(id);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
