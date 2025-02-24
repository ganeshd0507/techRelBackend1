package com.skbit.techrel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.skbit.techrel.entity.User;
import com.skbit.techrel.repo.StudentRepository;
import com.skbit.techrel.repo.UserRepository;



@Service
public class UserDetailService implements UserDetailsService{
  
	@Autowired
	private  UserRepository userRepo;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User byEmail = this.userRepo.findByEmail(email);
		System.out.println(byEmail);
		
		//loading user by user name
		return byEmail ;
	}

}
