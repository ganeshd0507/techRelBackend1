package com.skbit.techrel.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skbit.techrel.entity.Fee;
import com.skbit.techrel.exception.NotFoundException;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Long>{

	
}
