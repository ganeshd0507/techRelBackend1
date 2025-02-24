package com.skbit.techrel.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skbit.techrel.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	Student findByEmail(String email);
    Student findByMobileNo(long mobileNo);
    Student findByAadharNo(long aadharNo);
}
