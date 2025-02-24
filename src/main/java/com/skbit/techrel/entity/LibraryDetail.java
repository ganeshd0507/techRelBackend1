package com.skbit.techrel.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class LibraryDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String libraryName;
		
	private String area;
	
	@Embedded
	private Address address;
	
	private Long mobile;
	
	private String email;
	
	private String logo;
	
	private String longitude;
	
	private String latitude;
	
	
	@OneToOne(mappedBy = "librarydetail")
	@JsonBackReference
	private User user;
	
	
	@OneToOne(mappedBy = "libraryDetail", cascade = CascadeType.ALL)
	@JsonIgnore
	private Student student;
	
	
	@OneToMany(mappedBy = "libraryDetail",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Keyfeature> keyFeatures;
	
}
