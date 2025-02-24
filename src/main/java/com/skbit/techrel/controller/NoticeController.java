package com.skbit.techrel.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.skbit.techrel.entity.Notice;
import com.skbit.techrel.service.NoticeService;
import com.skbit.techrel.util.ApiResponse;

import helper.GenerateUniqueFilename;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    
    private static final List<String> ALLOWED_FILE_EXTENSIONS = Arrays.asList("pdf", "jpeg", "png", "jpg");
    
    @Value("${file.storage.directory}")
    private String FILE_DIRECTORY;

    Logger logger = LoggerFactory.getLogger(NoticeController.class);  // Fixed: Using NoticeController.class instead of StudentController.class
    
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestPart Notice notice, @RequestPart MultipartFile file) {
        // Extract file original name
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        
        logger.info("File extension: " + fileExtension);

        if (fileExtension == null || !ALLOWED_FILE_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            return new ResponseEntity<>(
                ApiResponse.builder().status(false)
                    .message("Invalid file type. Allowed types are pdf, jpeg, png, jpg.").build(),
                HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = GenerateUniqueFilename.generateUniqueFilename(file.getOriginalFilename());
            notice.setImg(fileName);

            File directory = new File(FILE_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            logger.info("Creating notice: " + notice);
            Notice createdNotice = noticeService.Create(notice);
            
            File serverFile = new File(directory, fileName);
            file.transferTo(serverFile);

            return new ResponseEntity<>(new ApiResponse("Notice created successfully", true), HttpStatus.CREATED);
        } catch (IOException e) {
            logger.error("Error creating notice: " + e.getMessage(), e);
            return new ResponseEntity<>(
                new ApiResponse("File upload failed: " + e.getMessage(), false),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable("id") Long id, @RequestBody Notice notice) {
        notice.setId(id);
        Notice updatedNotice = noticeService.update(notice);
        return new ResponseEntity<>(new ApiResponse("Notice updated successfully", true), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Notice> findById(@PathVariable("id") Long id) {
        Notice notice = noticeService.findById(id);
        return ResponseEntity.ok(notice);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Notice>> findAll() {
        List<Notice> notices = noticeService.findAll();
        return ResponseEntity.ok(notices);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id) {
        noticeService.delete(id);
        return new ResponseEntity<ApiResponse>(noticeService.delete(id), HttpStatus.OK);
    }
}