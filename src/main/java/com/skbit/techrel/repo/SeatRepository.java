package com.skbit.techrel.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skbit.techrel.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>{

}
