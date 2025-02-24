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

import com.skbit.techrel.entity.Keyfeature;
import com.skbit.techrel.entity.Task;
import com.skbit.techrel.service.KeyFeatureService;
import com.skbit.techrel.util.ApiResponse;

@RestController
@RequestMapping("/keyfeature")
public class KeyFeatureController {

	@Autowired
	private KeyFeatureService keyfeatureService;
	
	@PostMapping("/create")
	public ResponseEntity<Keyfeature> create(@RequestBody Keyfeature keyfeature){
		Keyfeature createdfeature = keyfeatureService.create(keyfeature);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdfeature);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Keyfeature> update(@PathVariable("id") Long id, @RequestBody Keyfeature keyfeature){
		keyfeature.setId(id);
	Keyfeature updatedFeature =	keyfeatureService.update(keyfeature);
	
	return ResponseEntity.ok(updatedFeature);
	}
	
	 @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id) {
		 keyfeatureService.delete(id);
	        return new ResponseEntity<ApiResponse>(keyfeatureService.delete(id), HttpStatus.OK);  
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Keyfeature> findById(@PathVariable("id") Long id) {
	    	Keyfeature keyfeature = keyfeatureService.findById(id);
	        return ResponseEntity.ok(keyfeature);
	    }

	    @GetMapping("/all")
	    public ResponseEntity<List<Keyfeature>> findAll() {
	        List<Keyfeature> features = keyfeatureService.findAll();
	        return ResponseEntity.ok(features);
	    }
	 
	
}
