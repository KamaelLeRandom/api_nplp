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

import com.kamael.nplp_api.model.Daily;
import com.kamael.nplp_api.model.DailyDTO;
import com.kamael.nplp_api.service.DailyService;

@RestController
@CrossOrigin
@RequestMapping("/daily")
public class DailyController {
	private final DailyService service;
	
	public DailyController(DailyService service) {
		this.service = service;
	}
	
	@PostMapping("/create")
	public ResponseEntity<DailyDTO> create(@RequestBody Daily daily) {
		try {
			DailyDTO res = service.create(daily);
			if (res == null)
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DailyDTO> read(@PathVariable Long id) {
		try {
			DailyDTO cut = service.read(id);
			if (cut == null)
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        return ResponseEntity.ok(cut);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping("/today")
	public ResponseEntity<List<DailyDTO>> readAllToday() {
		try {
			List<DailyDTO> res = service.readOrCreateToday();
			if (res == null)
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<DailyDTO>> readAll() {
		try {
			List<DailyDTO> res = service.readAll();
			if (res == null)
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<DailyDTO> update(@PathVariable Long id, @RequestBody Daily daily) {
		try {
			DailyDTO res = service.update(id, daily);
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
