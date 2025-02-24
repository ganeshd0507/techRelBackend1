package com.skbit.techrel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skbit.techrel.entity.Seat;
import com.skbit.techrel.exception.NotFoundException;
import com.skbit.techrel.repo.SeatRepository;
import com.skbit.techrel.util.ApiResponse;

@Service
public class SeatService {

	@Autowired
	private SeatRepository seatRepository;
	
	public Seat create(Seat seat) {
		return seatRepository.save(seat); 
	}
	
	public Seat update(Seat seat) {
		Seat createdSeat = seatRepository.findById(seat.getId())
				.orElseThrow(()->new NotFoundException("Seat not found"));
		createdSeat.setSeatNo(seat.getSeatNo());
		createdSeat.setStartTime(seat.getStartTime());
		createdSeat.setEndTime(seat.getEndTime());
		createdSeat.setTotalTime(seat.getTotalTime());
		
		return seatRepository.save(createdSeat);
	}
	
	public Seat createSeat(String seatNo) {
        Seat seat = new Seat();
        seat.setSeatNo(seatNo);
        return seatRepository.save(seat);
    }
	
	public Seat finById(Long id) {
		return seatRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("Seat not found with id"));
	}
	
	public List<Seat> findAll() {
		return seatRepository.findAll();
	}
	
	public ApiResponse delete(Long id) {
		Seat seat = seatRepository.findById(id)
		.orElseThrow(()->new NotFoundException("Seat not found with this id: "+ id));
		seatRepository.delete(seat);
		return ApiResponse.builder().message("Seat deleted successfully").status(true).build();
	}
	
}
