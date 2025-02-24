package com.skbit.techrel.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.skbit.techrel.entity.Course;
import com.skbit.techrel.entity.JobOpening;
import com.skbit.techrel.exception.AlreadyExistsException;
import com.skbit.techrel.exception.NotFoundException;
import com.skbit.techrel.repo.CourseRepo;
import com.skbit.techrel.service.CourseService;
import com.skbit.techrel.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/courses")
public class CourseController {

	Logger logger = LoggerFactory.getLogger(ExpensesController.class);

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseRepo courseRepo;

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addCourse(@Valid @RequestPart("file") MultipartFile file,
			@Valid @RequestPart("course") Course course) throws IOException {
		ApiResponse apiResponse = this.courseService.saveCourse(course, file);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

	}

	@GetMapping("/all")
	public ResponseEntity<List<Course>> getAllCourses() {
		List<Course> apiResponse = this.courseService.getAllCourses();
		return new ResponseEntity<List<Course>>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCourseById(@PathVariable int id) {
	    return courseService.getCourseById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteCourse(@PathVariable("id") int id) {
	    logger.info("Course ID: " + id);
	    return courseService.deleteCourse(id);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse> updateCourse(@PathVariable int id,
	        @RequestPart(value = "file", required = false) MultipartFile file,
	        @Valid @RequestPart("course") Course course) throws IOException {
	    
	    return courseService.updateCourse(id, course, file);
	}


	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
