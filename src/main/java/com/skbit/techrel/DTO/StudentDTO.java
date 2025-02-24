package com.skbit.techrel.DTO;

import com.skbit.techrel.entity.Student;

import lombok.Data;

@Data
public class StudentDTO {

	private Student student;
	private  String profilePic;
	
	
	public StudentDTO(Student student, String profilePic) {
		super();
		this.student = student;
		this.profilePic = profilePic;
	}
	
	
}
