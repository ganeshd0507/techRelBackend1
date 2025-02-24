package com.skbit.techrel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.skbit.techrel.entity.Fee;
import com.skbit.techrel.entity.JobOpening;
import com.skbit.techrel.service.JobOpeningService;
import com.skbit.techrel.util.ApiResponse;



@RestController
@RequestMapping("/job")
public class JopOpeningController {
  
  @Autowired
  private JobOpeningService jobOpeningSerivce;

  @PostMapping("/")
  public ResponseEntity<ApiResponse> createNewJob(@RequestBody JobOpening jobOpening) {

    ApiResponse apiResponse = this.jobOpeningSerivce.createNewOpening(jobOpening);
    return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

  }

//  @PutMapping("/")
//  public ResponseEntity<ApiResponse> updateJob(@RequestBody JobOpening jobOpening) {
//
//    ApiResponse apiResponse = this.jobOpeningSerivce.updateOpening(jobOpening);
//    return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
//
//  }
  
  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse> updateJob(@PathVariable("id") Long id, @RequestBody JobOpening jobOpening) {
	 jobOpening.setJobId(id);
    ApiResponse apiResponse = this.jobOpeningSerivce.updateOpening(jobOpening);
    return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

  }

  @GetMapping("/{id}")
  public ResponseEntity<JobOpening> findByJobId(@PathVariable("id") Long id) {

    JobOpening apiResponse = this.jobOpeningSerivce.findOpeningById(id);
    return new ResponseEntity<JobOpening>(apiResponse, HttpStatus.OK);

  }

  @GetMapping("/all")
  public ResponseEntity<List<JobOpening>> findAllJob() {

    List<JobOpening> apiResponse = this.jobOpeningSerivce.findAllOpening();
    return new ResponseEntity<List<JobOpening>>(apiResponse, HttpStatus.OK);

  }

  @GetMapping("/activejob")
  public ResponseEntity<List<JobOpening>> findAllActiveJob() {

    List<JobOpening> apiResponse = this.jobOpeningSerivce.findActiveJob();
    return new ResponseEntity<List<JobOpening>>(apiResponse, HttpStatus.OK);

  }

//  @PutMapping("/status/{id}")
//  public ResponseEntity<ApiResponse> updateJobStatus(@PathVariable long id,boolean status) {
//
//    ApiResponse response = this.jobOpeningSerivce.updateJobStatus(id, status);
//
//    return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
//
//  }
  
  @PutMapping("/status/{id}")
  public ResponseEntity<ApiResponse> toggleJobStatus(@PathVariable long id) {
      ApiResponse response = this.jobOpeningSerivce.toggleJobStatus(id);
      return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
