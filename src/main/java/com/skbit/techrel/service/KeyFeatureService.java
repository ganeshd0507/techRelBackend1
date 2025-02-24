package com.skbit.techrel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skbit.techrel.entity.Keyfeature;
import com.skbit.techrel.exception.NotFoundException;
import com.skbit.techrel.repo.KeyFeatureRepository;
import com.skbit.techrel.util.ApiResponse;

@Service
public class KeyFeatureService {

	
	@Autowired
	private KeyFeatureRepository keyFeatureRepository;
	
	
	public Keyfeature create(Keyfeature keyfeature) {
		return keyFeatureRepository.save(keyfeature);
	}
	
	public Keyfeature update(Keyfeature keyfeature) {
		Keyfeature updatedFeature = keyFeatureRepository.findById(keyfeature.getId())
				.orElseThrow(()->new NotFoundException("Feature not found"));
		
		updatedFeature.setFeatureDescription(keyfeature.getFeatureDescription());
		updatedFeature.setAvailable(keyfeature.getAvailable());
	
		return keyFeatureRepository.save(updatedFeature);
				
	}
	
	public Keyfeature findById(Long id) {
	return	keyFeatureRepository.findById(id)
			.orElseThrow(()->new NotFoundException("Feature not found with "+id + "id"));
	}
	
	public List<Keyfeature> findAll() {
		return keyFeatureRepository.findAll();
			
	}
	
	public ApiResponse delete(Long id) {
	 Keyfeature keyfeature =	keyFeatureRepository.findById(id)
			 .orElseThrow(()->new NotFoundException("Feature not found with this id"));
	 keyFeatureRepository.delete(keyfeature);
	 
	 return ApiResponse.builder().status(true).build();
	}
}
