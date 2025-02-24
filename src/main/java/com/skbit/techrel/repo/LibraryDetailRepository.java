package com.skbit.techrel.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skbit.techrel.entity.LibraryDetail;

@Repository
public interface LibraryDetailRepository extends JpaRepository<LibraryDetail, Long>{

	
}
