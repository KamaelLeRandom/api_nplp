package com.kamael.nplp_api.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kamael.nplp_api.model.Song;
import com.kamael.nplp_api.model.SongDTO;
import com.kamael.nplp_api.service.SongService;

@RestController
@CrossOrigin
@RequestMapping("/song")
public class SongController {
    private final SongService service;
    
    public SongController(SongService service) {
        this.service = service;
    }
    
	@PostMapping("/create")
	public SongDTO create(@RequestBody Song song) {
		return service.create(song);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SongDTO> read(@PathVariable Long id) {
		try {
			SongDTO song = service.read(id);
			if (song == null)
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        return ResponseEntity.ok(song);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<SongDTO>> readAll() {
		try {
			List<SongDTO> res = service.readAll();
			if (res == null)
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}	
	}
	
	@GetMapping("/filter")
	public ResponseEntity<Map<String, Object>> readAllFilter(
	        @RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(required = false) String search) {

	    try {
	        List<SongDTO> filteredSongs = service.readAll();

	        if (search != null && !search.isBlank()) {
	            String[] searchKeywords = search.toLowerCase().split("\\s+");

	            filteredSongs = filteredSongs.stream()
	                .filter(song -> {
	                    return Arrays.stream(searchKeywords).allMatch(keyword -> song.getTitle().toLowerCase().contains(keyword));
	                })
	                .collect(Collectors.toList());
	        }

	        int total = filteredSongs.size();
	        int totalPages = (int) Math.ceil((double) total / size);
	        int fromIndex = Math.min((page - 1) * size, total);
	        int toIndex = Math.min(fromIndex + size, total);
	        List<SongDTO> paginated = filteredSongs.subList(fromIndex, toIndex);

	        Map<String, Object> response = new HashMap<String, Object>();
	        response.put("songs", paginated);
	        response.put("totalPages", totalPages);
	        response.put("currentPage", page);

	        return ResponseEntity.ok(response);
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<SongDTO> update(@PathVariable Long id, @RequestBody Song song) {
		try {
			SongDTO songDTO = service.update(id, song);
			if (songDTO == null)
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        return ResponseEntity.ok(songDTO);
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
