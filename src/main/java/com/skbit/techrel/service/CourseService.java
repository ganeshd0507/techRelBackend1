package com.skbit.techrel.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.skbit.techrel.entity.Course;
import com.skbit.techrel.entity.Expenses;
import com.skbit.techrel.entity.JobOpening;
import com.skbit.techrel.exception.AlreadyExistsException;
import com.skbit.techrel.exception.CourseNotFoundException;
import com.skbit.techrel.exception.JobOpeningNotFoundException;
import com.skbit.techrel.exception.NotFoundException;
import com.skbit.techrel.repo.CourseRepo;
import com.skbit.techrel.util.ApiResponse;

@Service
public class CourseService {

	@Autowired
	private CourseRepo courseRepository;
	
	public ResponseEntity<ApiResponse> updateCourse(int id, Course course, MultipartFile file) throws IOException {
	    Optional<Course> existingCourse = courseRepository.findById(id);

	    if (existingCourse.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(ApiResponse.builder()
	                        .message("Course with ID " + id + " is not present.")
	                        .status(false)
	                        .build());
	    }

	    Course courseToUpdate = existingCourse.get();

	    // Update fields only if new values are provided
	    if (course.getTitle() != null) courseToUpdate.setTitle(course.getTitle());
	    if (course.getDuration() != null) courseToUpdate.setDuration(course.getDuration());
	    if (course.getLiveProjectDuration() != null) courseToUpdate.setLiveProjectDuration(course.getLiveProjectDuration());
	    if (course.getSyllabus() != null) courseToUpdate.setSyllabus(course.getSyllabus());
	    if (course.getBrochures() != null) courseToUpdate.setBrochures(course.getBrochures());
	    if (course.getCost() != null) courseToUpdate.setCost(course.getCost());

	    // Handle file upload if a new file is provided
	    if (file != null && !file.isEmpty()) {
	        String fileName = file.getOriginalFilename();
	        String uploadDir = "src/main/resources/static/course-images/";

	        // Create directory if it doesn't exist
	        File directory = new File(uploadDir);
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }

	        // Save file and update course logo
	        Files.write(Paths.get(uploadDir + fileName), file.getBytes());
	        courseToUpdate.setLogo(fileName);
	    }

	    // Save updated course details
	    courseRepository.save(courseToUpdate);

	    return ResponseEntity.ok(
	            ApiResponse.builder()
	                    .message("Course updated successfully.")
	                    .status(true)
	                    .build());
	}


	public ApiResponse saveCourse(Course course, MultipartFile file) throws IOException {
		Optional<Course> existingCourse = courseRepository.findByTitle(course.getTitle());
		if (existingCourse.isPresent()) {

			throw new AlreadyExistsException(course.getTitle() + " already exists!");
		}

		if (file != null && !file.isEmpty()) {
			String fileExtension = Objects.requireNonNull(file.getOriginalFilename())
					.substring(file.getOriginalFilename().lastIndexOf("."));
			String fileName = UUID.randomUUID() + fileExtension;

			Path uploadPath = Paths.get("src/main/resources/static/course-images/");

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			// Save file to the directory
			Path filePath = uploadPath.resolve(fileName);
			Files.write(filePath, file.getBytes());
			course.setLogo(fileName);
		} else {
			throw new IllegalArgumentException("File is required!");
		}

		this.courseRepository.save(course);

		return ApiResponse.builder().status(true).message("Course added successfully.").build();
	}

	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	public Optional<Course> getCourseByIds(int id) {
		return courseRepository.findById(id);
	}


	public ResponseEntity<?> getCourseById(int id) {
		Optional<Course> course = courseRepository.findById(id);

		if (course.isPresent()) {
			return ResponseEntity.ok(course.get()); // data found
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					ApiResponse.builder().status(false).message("Course with ID " + id + " is not present.").build());
		}
	}

	public ResponseEntity<ApiResponse> deleteCourse(int id) {
		Optional<Course> course = courseRepository.findById(id);

		if (course.isPresent()) {
			courseRepository.delete(course.get());
			return ResponseEntity
					.ok(ApiResponse.builder().message("Course deleted successfully.").status(true).build());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					ApiResponse.builder().message("Course with ID " + id + " is not present.").status(false).build());
		}
	}

}
