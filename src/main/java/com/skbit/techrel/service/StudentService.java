package com.skbit.techrel.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skbit.techrel.entity.Seat;
import com.skbit.techrel.entity.Student;
import com.skbit.techrel.exception.AlreadyExistsException;
import com.skbit.techrel.exception.NotFoundException;
import com.skbit.techrel.repo.SeatRepository;
import com.skbit.techrel.repo.StudentRepository;

import java.nio.channels.AlreadyBoundException;
import java.util.List;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Student create(Student student) {

		Student studentByMobile = studentRepository.findByMobileNo(student.getMobileNo());
		if (studentByMobile != null) {
			throw new AlreadyExistsException("Student already exists with given mobile number" + student.getMobileNo());
		}

		Student studentByAadhar = studentRepository.findByAadharNo(student.getAadharNo());
		if (studentByAadhar != null) {
			throw new AlreadyExistsException("Student already exist with given aadhar number" + student.getAadharNo());
		}

		Student studentByEmail = studentRepository.findByEmail(student.getEmail());
		if (studentByEmail != null) {
			throw new AlreadyExistsException("Student already exist with given eamil" + student.getEmail());
		}

		student.setPassword(passwordEncoder.encode(student.getPassword()));

		return studentRepository.save(student);

	}
	
	public void assignSeatToStudents(Long seatId, List<Long> studentIds) {
	    Seat seat = seatRepository.findById(seatId)
	            .orElseThrow(() -> new NotFoundException("Seat with ID " + seatId + " not found"));

	    List<Student> students = studentRepository.findAllById(studentIds);

	    if (students.isEmpty()) {
	        throw new NotFoundException("No students found for the given IDs");
	    }

	    for (Student student : students) {
	        student.setSeat(seat);
	    }

	    studentRepository.saveAll(students);
	    System.out.println("Seat " + seat.getSeatNo() + " assigned to students: " + studentIds);
	}


	public Student update(Student student) {
		Student existingStudent = studentRepository.findById(student.getId())
				.orElseThrow(() -> new NotFoundException("Student not found with ID: " + student.getId()));
		System.out.println("existing student" + existingStudent);
		existingStudent.setFatherName(student.getFatherName());
		existingStudent.setMotherName(student.getMotherName());

		existingStudent.setMobileNo(student.getMobileNo());
		existingStudent.setDateOfBirth(student.getDateOfBirth());

		existingStudent.setAadharNo(student.getAadharNo());
		existingStudent.setAddress(student.getAddress());
		existingStudent.setEmail(student.getEmail());

		existingStudent.setGender(student.getGender());
		return studentRepository.save(existingStudent);
	}

	public void delete(Long id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Student not found with ID: " + id));
		studentRepository.delete(student);
	}

	public Student findById(Long id) {
		return studentRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Student not found with ID: " + id));
	}

	
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	public Student findByEmail(String email) {

		return this.studentRepository.findByEmail(email);
	}
	
}
