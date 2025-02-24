package com.skbit.techrel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skbit.techrel.entity.Fee;
import com.skbit.techrel.entity.Student;
import com.skbit.techrel.entity.User;
import com.skbit.techrel.exception.NotFoundException;
import com.skbit.techrel.repo.FeeRepository;
import com.skbit.techrel.repo.StudentRepository;
import com.skbit.techrel.repo.UserRepository;
import com.skbit.techrel.util.ApiResponse;

@Service
public class FeeService {

	@Autowired
	private FeeRepository feeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	

	public Student create(Fee fee, String email) {
		 
		Student student = studentRepository.findByEmail(email);
		
		if(student==null) {
			throw new RuntimeException("student not found with email"+ email);
		}
		List<Fee> fees = student.getFees();
		fees.add(fee);
		student.setFees(fees);
		return studentRepository.save(student);
	}
	
	public Fee update(Fee fee) {
		Fee updatedfee = feeRepository.findById(fee.getId())
				.orElseThrow(()->new NotFoundException("Fee not found"));
		
		updatedfee.setYear(fee.getYear());
		updatedfee.setMonth(fee.getMonth());
		updatedfee.setDate(fee.getDate());
		updatedfee.setPaidFee(fee.getPaidFee());
		updatedfee.setFeeAmt(fee.getFeeAmt());
		updatedfee.setJoiningDate(fee.getJoiningDate());
		updatedfee.setDuration(fee.getDuration());
		updatedfee.setPaymentMode(fee.getPaymentMode());
		updatedfee.setExpirationDate(fee.getExpirationDate());
		
		 
	
		return feeRepository.save(updatedfee);
				
	}
	
	public Fee findById(Long id) {
	return	feeRepository.findById(id)
			.orElseThrow(()->new NotFoundException("Feature not found with "+id + "id"));
	}
	
	public List<Fee> findAll() {
		return feeRepository.findAll();
			
	}
	
	public ApiResponse delete(Long id) {
	 Fee fee =	feeRepository.findById(id)
			 .orElseThrow(()->new NotFoundException("Feature not found with this id"));
	 feeRepository.delete(fee);
	 
	 return ApiResponse.builder().message("Fee deleted successfully").build();
	}
}
