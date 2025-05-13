package com.kamael.nplp_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kamael.nplp_api.model.Song;
import com.kamael.nplp_api.model.SongDTO;
import com.kamael.nplp_api.repository.SongRepository;

@Service
public class SongService implements SongServiceInterface {
	private SongRepository repository;
	
	public SongService(SongRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public SongDTO create(Song song) {
		try {
			return SongDTO.convertToDTO(repository.save(song));
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@Override
	public SongDTO read(Long id) {
		try {
			Song song = repository.findById(id).orElse(null);
			return SongDTO.convertToDTO(song);
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@Override
	public List<SongDTO> readAll() {
	    try {
	        return repository.findAll().stream()
	            .map(SongDTO::convertToDTO)
	            .collect(Collectors.toList());
	    }
	    catch (Exception ex) {
	        System.out.println(ex.toString());
	        return null;
	    }
	}


	@Override
	public SongDTO update(Long id, Song song) {
		try {
			return SongDTO.convertToDTO(repository.findById(id).map(p -> {
				p.setTitle(song.getTitle());
				p.setLyric(song.getLyric());
				p.setPublishAt(song.getPublishAt());
				p.setDuration(song.getDuration());
				p.setAuthors(song.getAuthors());
				return repository.save(p);
			}).orElseGet(null)) ;
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
