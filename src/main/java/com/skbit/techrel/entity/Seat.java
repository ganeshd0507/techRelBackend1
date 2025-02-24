package com.skbit.techrel.entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String seatNo;     
	
	private LocalTime startTime;
	
	private LocalTime endTime;
	
	private LocalTime totalTime;
	
	@OneToMany(mappedBy = "seat" ,cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
    private List<Student> students = new ArrayList<>();
	
	
}
