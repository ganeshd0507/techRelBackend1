package com.skbit.techrel.entity;


import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDate regDate;  
    private String profilePic;  
    
    @NotNull(message="Class number cannot be null")
	private String classNo;
    
    @ManyToOne
    @JoinColumn(name="seat_id")
    @JsonBackReference
    private Seat seat;
    
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "library_detail_id")
    private LibraryDetail libraryDetail;
    
    @OneToMany(cascade =CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Fee> fees;

   
   

}
