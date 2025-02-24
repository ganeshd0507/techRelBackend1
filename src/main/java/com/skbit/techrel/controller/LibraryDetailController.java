package com.skbit.techrel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skbit.techrel.entity.LibraryDetail;
import com.skbit.techrel.service.LibraryDetailService;
import com.skbit.techrel.util.ApiResponse;

@RestController
@RequestMapping("/librarydetail")
public class LibraryDetailController {

	@Autowired
	private LibraryDetailService libraryDetailService;
	
	@PostMapping("/create")
	public ResponseEntity<LibraryDetail> create(@RequestBody LibraryDetail libraryDetail){
      LibraryDetail createdLbDetail =		libraryDetailService.create(libraryDetail);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdLbDetail);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<LibraryDetail> update(@PathVariable("id") Long id, @RequestBody LibraryDetail libraryDetail){
		libraryDetail.setId(id);
		LibraryDetail updatedLbDetail = libraryDetailService.update(libraryDetail);
		return ResponseEntity.ok(updatedLbDetail);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<LibraryDetail> findById(@PathVariable("id") Long id){
		LibraryDetail librarybDetail = libraryDetailService.finById(id);
		return ResponseEntity.ok(librarybDetail);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<LibraryDetail>> finAll(){
		List<LibraryDetail> libraryDetails = libraryDetailService.findAll();
		return ResponseEntity.ok(libraryDetails);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id){
		return new ResponseEntity<ApiResponse>(libraryDetailService.delete(id),HttpStatus.OK);
	}
	
}
