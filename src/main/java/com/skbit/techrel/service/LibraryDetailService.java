package com.skbit.techrel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skbit.techrel.entity.LibraryDetail;
import com.skbit.techrel.entity.Seat;
import com.skbit.techrel.exception.NotFoundException;
import com.skbit.techrel.repo.LibraryDetailRepository;
import com.skbit.techrel.util.ApiResponse;

@Service
public class LibraryDetailService {

	
	@Autowired
	private LibraryDetailRepository libraryDetailRepository;
	
	public LibraryDetail create(LibraryDetail libraryDetail) {
		return libraryDetailRepository.save(libraryDetail);
	}
	
	public LibraryDetail update(LibraryDetail libraryDetail) {
		LibraryDetail createdLibraryDetail = libraryDetailRepository.findById(libraryDetail.getId())
				.orElseThrow(()-> new NotFoundException("Library details not found"));
		
		createdLibraryDetail.setLibraryName(libraryDetail.getLibraryName());
		createdLibraryDetail.setArea(libraryDetail.getArea());
		createdLibraryDetail.setAddress(libraryDetail.getAddress());

		createdLibraryDetail.setMobile(libraryDetail.getMobile());
		createdLibraryDetail.setLogo(libraryDetail.getLogo());
		createdLibraryDetail.setLongitude(libraryDetail.getLongitude());
		createdLibraryDetail.setLatitude(libraryDetail.getLatitude());
		
		return libraryDetailRepository.save(createdLibraryDetail);
		
	}
	
	public LibraryDetail finById(Long id) {
		return libraryDetailRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("Seat not found with id"));
	}
	
	public List<LibraryDetail> findAll() {
		return libraryDetailRepository.findAll();
	}
	
	public ApiResponse delete(Long id) {
		LibraryDetail librarydetail = libraryDetailRepository.findById(id)
		.orElseThrow(()->new NotFoundException("Seat not found with this id: "+ id));
		libraryDetailRepository.delete(librarydetail);
		
		return ApiResponse.builder().message("library deleted successfully").status(true).build();
	}
	 
}
