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

import com.skbit.techrel.entity.Seat;
import com.skbit.techrel.service.SeatService;
import com.skbit.techrel.util.ApiResponse;

@RestController
@RequestMapping("/seat")
public class SeatController {

	@Autowired
	private SeatService seatService;
	
	@PostMapping("/create")
	public ResponseEntity<Seat> create(@RequestBody Seat seat){
		System.out.println("Object here: "+seat);
		Seat createdseat = seatService.create(seat);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdseat);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Seat> update(@PathVariable("id") Long id, @RequestBody Seat seat){
		Seat updatedSeat = seatService.update(seat);
		return ResponseEntity.ok(updatedSeat);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Seat> findById(@PathVariable("id") Long id){
		Seat seat = seatService.finById(id);
		return ResponseEntity.ok(seat);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Seat>> findAll(){
		List<Seat> seats = seatService.findAll();
		return ResponseEntity.ok(seats);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id){
		seatService.delete(id);
		return new ResponseEntity<ApiResponse>(seatService.delete(id), HttpStatus.OK);
	}
}
