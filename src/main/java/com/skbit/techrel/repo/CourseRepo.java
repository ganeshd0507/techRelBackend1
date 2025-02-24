package com.skbit.techrel.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skbit.techrel.entity.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
	
	 Optional<Course> findByTitle(String title);

}

