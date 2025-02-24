package com.skbit.techrel.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skbit.techrel.entity.Expenses;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long>{

}
