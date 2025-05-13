package com.kamael.nplp_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamael.nplp_api.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
