package com.kamael.nplp_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamael.nplp_api.model.Cut;

public interface CutRepository extends JpaRepository<Cut, Long> {
    List<Cut> findByDifficulty_Id(Long difficultyId);

}
