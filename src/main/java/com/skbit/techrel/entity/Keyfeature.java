package com.skbit.techrel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Keyfeature {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Boolean available;
	
	private String featureDescription;
	
	private String featureType;
	
	
	@ManyToOne
	@JoinColumn(name="library_id")
	@JsonIgnore
	private LibraryDetail libraryDetail;
	
}
