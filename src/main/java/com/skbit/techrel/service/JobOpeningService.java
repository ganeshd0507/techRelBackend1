package com.skbit.techrel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skbit.techrel.entity.Fee;
import com.skbit.techrel.entity.JobOpening;
import com.skbit.techrel.exception.JobOpeningNotFoundException;
import com.skbit.techrel.exception.NotFoundException;
import com.skbit.techrel.repo.OpeningRepository;
import com.skbit.techrel.util.ApiResponse;




@Service
public class JobOpeningService  {

    @Autowired
    private OpeningRepository openingRepository;


    public ApiResponse createNewOpening(JobOpening jopOpening) {

        Optional<JobOpening> byTitle = this.openingRepository.findByTitle(jopOpening.getTitle());

        if (byTitle.isPresent()) {
            return ApiResponse.builder().status(true)
                    .message("job is already exist with given title" + jopOpening.getTitle()).build();
        }

        this.openingRepository.save(jopOpening);

        return ApiResponse.builder().status(true).message("job is successfully created").build();
    }


//    public ApiResponse updateOpening(JobOpening jopOpening) {
//
//        JobOpening jobs = this.openingRepository.findById(jopOpening.getJobId())
//                .orElseThrow(() -> new JobOpeningNotFoundException());
//
//            this.openingRepository.save(jopOpening);
//      
//
//  
//     return    ApiResponse.builder().status(true).message("job is updated successfully").build();
//
//    
//    }
    

    public ApiResponse updateOpening(JobOpening jobs) {

        JobOpening job = this.openingRepository.findById(jobs.getJobId())
                .orElseThrow(() -> new JobOpeningNotFoundException());
        
        job.setTitle(jobs.getTitle());
        job.setLocation(jobs.getLocation());
        job.setPosition(jobs.getPosition());
        job.setJobType(jobs.getJobType());
        job.setStatus(jobs.getStatus());
        job.setQualification(jobs.getQualification());
        
        job.setQualification(jobs.getQualification());
        job.setExperience(jobs.getExperience());
        job.setJobDescription(jobs.getJobDescription());
        job.setKeyResponsibility(jobs.getKeyResponsibility());
        job.setRequiredSkill(jobs.getKeyResponsibility());
        job.setRequiredSkill(jobs.getRequiredSkill());
        
        job.setCreatedAt(jobs.getCreatedAt());
        job.setCreatedBy(jobs.getCreatedBy());
        job.setCompanyName(jobs.getCompanyName());
        job.setEducationCriteria(jobs.getEducationCriteria());
        job.setPercentageCriteria(jobs.getPercentageCriteria());
        job.setPassoutYear(jobs.getPassoutYear());
        job.setPercentageCriteria(jobs.getPercentageCriteria());
        job.setPassoutYear(jobs.getPassoutYear());
        
        job.setSkills(jobs.getSkills());
        job.setJobLocation(jobs.getJobLocation());
        job.setInterviewMode(jobs.getInterviewMode());
        job.setInterviewDate(jobs.getInterviewDate());
        
        job.setBond(jobs.getBond());

        this.openingRepository.save(job);
            
        return  ApiResponse.builder().status(true).message("job is updated successfully").build();

    }


    public JobOpening findOpeningById(Long id) {
        return this.openingRepository.findById(id).orElseThrow(() -> new JobOpeningNotFoundException());
    }

    public List<JobOpening> findAllActiveJobOpening(Boolean openingstatus) {
        return this.openingRepository.findAll();
    }


    public List<JobOpening> findAllOpening() {
        return this.openingRepository.findAll();
    }


	public List<JobOpening> findActiveJob() {
		
		return this.openingRepository.findByStatusTrue();	
		}


//    public ApiResponse updateJobStatus(long id, boolean status) {
//         JobOpening job = this.openingRepository.findById(id).orElseThrow(() -> new JobOpeningNotFoundException("job not found with given id"+id));
//         job.setStatus(status);
//         this.openingRepository.save(job);
//         return    ApiResponse.builder().status(true).message("job status is updated successfully").build();
//
//    }
	
	public ApiResponse toggleJobStatus(long id) {
	    JobOpening job = this.openingRepository.findById(id)
	            .orElseThrow(() -> new JobOpeningNotFoundException("Job not found with given ID: " + id));

	    job.setStatus(!job.getStatus());

	    this.openingRepository.save(job);

	    return ApiResponse.builder()
	            .status(true)
	            .message("Job status updated successfully. New status: " + job.getStatus())
	            .build();
	}


}
