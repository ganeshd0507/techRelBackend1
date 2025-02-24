package com.skbit.techrel.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skbit.techrel.entity.Keyfeature;

@Repository
public interface KeyFeatureRepository extends JpaRepository<Keyfeature, Long>{

	
}
