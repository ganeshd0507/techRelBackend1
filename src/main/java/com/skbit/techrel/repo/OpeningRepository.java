package com.skbit.techrel.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skbit.techrel.entity.JobOpening;



public interface OpeningRepository extends JpaRepository<JobOpening,Long> {

    public List<JobOpening> findByStatusTrue();
    public Optional<JobOpening> findByTitle(String title);
}
