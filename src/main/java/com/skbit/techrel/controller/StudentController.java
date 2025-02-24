package com.skbit.techrel.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.skbit.techrel.DTO.StudentDTO;
import com.skbit.techrel.entity.Student;
import com.skbit.techrel.service.StudentService;
import com.skbit.techrel.util.ApiResponse;

import helper.GenerateUniqueFilename;
import jakarta.servlet.annotation.MultipartConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    
    private static final List<String> ALLOWED_FILE_EXTENSIONS = Arrays.asList("pdf", "jpeg", "png", "jpg");
    
    @Value("${file.storage.directory}")
	private String FILE_DIRECTORY;

    Logger logger=LoggerFactory.getLogger(StudentController.class);

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestPart Student student, @RequestPart MultipartFile file  ) {
    	
    Student students = this.studentService.findByEmail(student.getEmail());
    
    if(students !=null) {
    	
       
    	return new ResponseEntity<ApiResponse>( ApiResponse.builder().message("student already register with email" +student.getEmail())
    			.status(false).build(),HttpStatus.BAD_GATEWAY);
    	
    }
    	
    	// extrack file original file name
    	String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
    	
    	logger.info("file extension"+fileExtension);

    	if (fileExtension == null || !ALLOWED_FILE_EXTENSIONS.contains(fileExtension.toLowerCase())) {
			return new ResponseEntity<>(
					ApiResponse.builder().status(false)
							.message("Invalid file type. Allowed types are pdf, jpeg, png, jpg.").build(),
					HttpStatus.BAD_REQUEST);
		}
    	
    	
    	try {
			String fileName=GenerateUniqueFilename.generateUniqueFilename(file.getOriginalFilename());
          student.setProfilePic(fileName);

			File directory = new File(FILE_DIRECTORY);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			


	    	logger.info("create stdent api"+student);
	        Student createdStudent = studentService.create(student);
	        File serverFile = new File(directory, fileName);

			file.transferTo(serverFile);
	        return new ResponseEntity<>(new ApiResponse("Student created successfully", true), HttpStatus.CREATED);
		} catch (IOException e) {
			return new ResponseEntity<>(new ApiResponse("File upload failed: " + e.getMessage(), false),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

   }
    
    
    @GetMapping("/profile-pic/{id}")
    public ResponseEntity<Resource> getProfilePic(@PathVariable Long id, @RequestHeader(value = "Accept", required = false) String acceptHeader) {
        Student student = studentService.findById(id);
        
        if (student == null || student.getProfilePic() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(FILE_DIRECTORY, student.getProfilePic()).normalize();
            File file = filePath.toFile();
             
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(filePath.toUri());

            if ("application/json".equalsIgnoreCase(acceptHeader)) {
                String imageUrl = "/students/profile-pic/" + id;
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(resource);
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + student.getProfilePic() + "\"")
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    
}

    @PostMapping("/assign-seat/{seatId}")
    public ResponseEntity<String> assignSeat(@PathVariable Long seatId, @RequestBody List<Long> studentIds) {
        studentService.assignSeatToStudents(seatId, studentIds);
        return ResponseEntity.ok("Seat assigned successfully.");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        student.setId(id);
        
        logger.info("student obj"+ student);
        
        studentService.update(student);
        return new ResponseEntity<>(new ApiResponse("Student updated successfully", true), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable("id") Long id) {
        studentService.delete(id);
        return new ResponseEntity<>(new ApiResponse("Student deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable("id") Long id) {
        Student student = studentService.findById(id);
        
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
    
        // Convert profile image to Base64
        String base64Image = getImageAsBase64(student.getProfilePic());
    
        // Create DTO response
        StudentDTO studentDTO = new StudentDTO(student, base64Image);
    
        return ResponseEntity.ok(studentDTO);
    }
    
    
    @GetMapping("/email")
    public ResponseEntity<Student> getByEmail(@RequestParam String email) {
        Student student = studentService.findByEmail(email);
        
        if (student == null) {
            return ResponseEntity.notFound().build(); // 404 response if student not found
        }
        return ResponseEntity.ok(student);
    }
    
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<Student> students = studentService.findAll();

        List<StudentDTO> studentDTOs = students.stream().map(student -> 
            new StudentDTO(
                student,  
                getImageAsBase64(student.getProfilePic()) 
            )
        ).collect(Collectors.toList());

        return ResponseEntity.ok(studentDTOs);
    }

    private String getImageAsBase64(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null; 
        }

        try {
            Path filePath = Paths.get(FILE_DIRECTORY, fileName).normalize();
            if (!Files.exists(filePath)) {
                System.err.println("Image file NOT FOUND: " + filePath.toAbsolutePath());
                return null;
            }
            byte[] imageBytes = Files.readAllBytes(filePath); // Read file as byte stream
            return Base64.getEncoder().encodeToString(imageBytes); // Convert to Base64
        } catch (IOException e) {
            System.err.println("Error reading image file: " + e.getMessage());
            return null;
        }
    }

    
   
     
}
