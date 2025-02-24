package com.skbit.techrel.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skbit.techrel.entity.Expenses;
import com.skbit.techrel.service.ExpensesService;
import com.skbit.techrel.util.ApiResponse;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {

	@Autowired
	private ExpensesService expensesService;
	
	Logger logger=LoggerFactory.getLogger(ExpensesController.class);
	
	@PostMapping("/create")
	public ResponseEntity<Expenses> create(@RequestBody Expenses expenses){
		Expenses expense= expensesService.create(expenses);
		return ResponseEntity.status(HttpStatus.CREATED).body(expense);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Expenses> update(@PathVariable("id") Long id,@RequestBody Expenses expenses){
		expenses.setId(id);
		Expenses expense = expensesService.update(expenses);
		return ResponseEntity.ok(expense);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Expenses> findById(@PathVariable("id") Long id){
		Expenses expense = expensesService.findById(id);
		return ResponseEntity.ok(expense);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Expenses>> findAll(){
		List<Expenses> expenses = expensesService.findAll();
		return ResponseEntity.ok(expenses);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id){
		
		logger.info("expense id"+ id);
//		ApiResponse delete = expensesService.delete(id);
		
		return new ResponseEntity<ApiResponse>(expensesService.delete(id), HttpStatus.OK);
	}
}
