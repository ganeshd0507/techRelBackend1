package com.skbit.techrel.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JobOpening {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long jobId;

    private String title;
    private String location;
    private String position;

    private String jobType;
    private Boolean status;
    private String qualification;
    private String experience;
    @Size(max = 1000)
    private String jobDescription;
    @Size(max = 1000)
    private String keyResponsibility;
    @Size(max = 1000)
    private String requiredSkill;
    private LocalDate createdAt;
    private String createdBy;
    
    
    
    private String companyName;
    private String educationCriteria;
    private String percentageCriteria;
    private String passoutYear;
    private String skills;
    private String jobLocation;
    private String interviewMode;
    private String interviewDate;
    private String bond;
    
    



}
