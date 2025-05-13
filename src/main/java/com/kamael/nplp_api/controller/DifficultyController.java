package com.kamael.nplp_api.controller;

import java.util.List;

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

import com.kamael.nplp_api.model.Difficulty;
import com.kamael.nplp_api.model.DifficultyDTO;
import com.kamael.nplp_api.service.DifficultyService;

@RestController
@CrossOrigin
@RequestMapping("/difficulty")
public class DifficultyController {
	private final DifficultyService service;
	
	public DifficultyController(DifficultyService service) {
		this.service = service;
	}
	
	@PostMapping("/create")
	public ResponseEntity<DifficultyDTO> create(@RequestBody Difficulty diff) {
		try {
			DifficultyDTO res = service.create(diff);
			if (res == null)
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DifficultyDTO> read(@PathVariable Long id) {
		try {
			DifficultyDTO res = service.read(id);
			if (res == null)
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<DifficultyDTO>> readAll() {
		try {
			List<DifficultyDTO> res = service.readAll();
			if (res == null)
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<DifficultyDTO> update(@PathVariable Long id, @RequestBody Difficulty diff) {
		try {
			DifficultyDTO res = service.update(id, diff);
			if (res == null)
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        return ResponseEntity.ok(res);
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
