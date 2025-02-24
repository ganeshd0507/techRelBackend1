package com.skbit.techrel.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skbit.techrel.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
	Optional<Task> findById(Long id);

}
