package com.kamael.nplp_api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kamael.nplp_api.model.Daily;

public interface DailyRepository extends JpaRepository<Daily, Long> {
    @Query("SELECT d FROM Daily d WHERE d.createFor = :date")
    List<Daily> findByDate(@Param("date") LocalDate date);
}
