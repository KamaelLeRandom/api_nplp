package com.kamael.nplp_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamael.nplp_api.model.Song;

public interface SongRepository extends JpaRepository<Song, Long> {

}
