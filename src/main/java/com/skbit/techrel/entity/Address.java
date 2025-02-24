package com.skbit.techrel.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class Address {

	@NotNull(message="Pincode cannot be null")
	private Long pincode;
	
	@NotNull(message="State cannot be null")
	private String state;
	
	@NotNull(message = "City cannot be null")
	private String city;
}
