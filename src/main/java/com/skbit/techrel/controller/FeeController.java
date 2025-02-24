package com.skbit.techrel.controller;

import java.util.List;


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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skbit.techrel.entity.Fee;
import com.skbit.techrel.entity.Student;
import com.skbit.techrel.service.FeeService;
import com.skbit.techrel.util.ApiResponse;

@RestController
@RequestMapping("/fee")
public class FeeController {

	@Autowired
	private FeeService feeService;
	
	@PostMapping("/create")
	public ResponseEntity<Student> create(@RequestParam String email,@RequestBody Fee fee ){
		Student createdtfee =feeService.create(fee, email);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdtfee);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Fee> update(@PathVariable("id") Long id,@RequestBody Fee fee){
		fee.setId(id);
		Fee updatedfee =feeService.update(fee);
		return ResponseEntity.ok(updatedfee);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Fee> findById(@PathVariable("id") Long id){
	Fee fee=	feeService.findById(id);
	return ResponseEntity.ok(fee);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Fee>> findAll(){
		List<Fee> fees =feeService.findAll();
		return ResponseEntity.ok(fees);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id){
		feeService.delete(id);
		return new ResponseEntity<ApiResponse>(feeService.delete(id), HttpStatus.OK);
	}
}
