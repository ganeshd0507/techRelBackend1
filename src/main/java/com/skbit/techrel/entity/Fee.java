package com.skbit.techrel.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Fee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String year;
	
	private String month;
	
	private LocalDate date;

	private Long remainingFee;
	
	private Long paidFee;
	
	
	private Long feeAmt;
	
	private LocalDate joiningDate;
	
	private String duration;
	
	private String paymentMode;
	
	private LocalDate expirationDate;

	
	}
