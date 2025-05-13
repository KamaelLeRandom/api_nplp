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

import com.kamael.nplp_api.model.Author;
import com.kamael.nplp_api.model.AuthorDTO;
import com.kamael.nplp_api.service.AuthorService;

@RestController
@CrossOrigin
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService service;
    
    public AuthorController(AuthorService service) {
        this.service = service;
    }
    
	@PostMapping("/create")
	public ResponseEntity<AuthorDTO> create(@RequestBody Author player) {
		try {
			AuthorDTO res = service.create(player);
			if (res == null)
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AuthorDTO> read(@PathVariable Long id) {
		try {
			AuthorDTO res = service.read(id);
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
	        List<AuthorDTO> filteredAuthors = service.readAll();

	        if (search != null && !search.isBlank()) {
	            String[] searchKeywords = search.toLowerCase().split("\\s+");

	            filteredAuthors = filteredAuthors.stream()
	                .filter(author -> {
	                    return Arrays.stream(searchKeywords).allMatch(keyword -> 
	                    	author.getNickname().toLowerCase().contains(keyword) ||
	                    	author.getFirstname().toLowerCase().contains(keyword) ||
	                    	author.getLastname().toLowerCase().contains(keyword)
	                    );
	                })
	                .collect(Collectors.toList());
	        }

	        int total = filteredAuthors.size();
	        int totalPages = (int) Math.ceil((double) total / size);
	        int fromIndex = Math.min((page - 1) * size, total);
	        int toIndex = Math.min(fromIndex + size, total);
	        List<AuthorDTO> paginated = filteredAuthors.subList(fromIndex, toIndex);

	        Map<String, Object> response = new HashMap<String, Object>();
	        response.put("songs", paginated);
	        response.put("totalPages", totalPages);
	        response.put("currentPage", page);

	        return ResponseEntity.ok(response);
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	@GetMapping
	public ResponseEntity<List<AuthorDTO>> readAll() {
		try {
			List<AuthorDTO> res = service.readAll();
			if (res == null)
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        return ResponseEntity.ok(res);
		}
		catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<AuthorDTO> update(@PathVariable Long id, @RequestBody Author player) {
		try {
			AuthorDTO res = service.update(id, player);
			if (res == null)
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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
