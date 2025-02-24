package com.skbit.techrel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skbit.techrel.entity.Expenses;
import com.skbit.techrel.exception.NotFoundException;
import com.skbit.techrel.repo.ExpensesRepository;
import com.skbit.techrel.util.ApiResponse;

@Service
public class ExpensesService {

	@Autowired
	private ExpensesRepository expensesRepository;
	
	public Expenses create(Expenses expenses) {
		return expensesRepository.save(expenses);
	}
	
	public Expenses update(Expenses expenses) {
	Expenses updatedExpenses =	expensesRepository.findById(expenses.getId())
			.orElseThrow(()->new NotFoundException("Expenses not found"));
	
	updatedExpenses.setPurpose(expenses.getPurpose());
	updatedExpenses.setSubject(expenses.getSubject());
	updatedExpenses.setAmount(expenses.getAmount());
	updatedExpenses.setDescription(expenses.getDescription());
	updatedExpenses.setDate(expenses.getDate());
	
	return expensesRepository.save(updatedExpenses);
	}
	
	public Expenses findById(Long id) {
		return expensesRepository.findById(id)
				.orElseThrow(()->new NotFoundException("Expenses not found with "+id+"this id"));
	}
	
	public List<Expenses> findAll() {
	return expensesRepository.findAll();		
	}
	
	public ApiResponse delete(Long id) {
		Expenses expense = expensesRepository.findById(id)
		.orElseThrow(()->new NotFoundException("Expenses not fount with this"+id ));
		
		
		expensesRepository.delete(expense);
		return ApiResponse.builder().message("Expense deleted successfully").status(true).build();
	}
}
