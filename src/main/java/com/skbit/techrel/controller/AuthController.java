package com.skbit.techrel.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skbit.techrel.configuration.JwtHelper;
import com.skbit.techrel.entity.User;
import com.skbit.techrel.exception.InvalidPasswordException;
import com.skbit.techrel.service.UserService;
import com.skbit.techrel.util.JwtRequest;
import com.skbit.techrel.util.JwtResponse;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtHelper jwtHelper;

	@PostMapping("/login")

	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws Exception {

		System.out.println(request.getName());
		UserDetails userDetail = this.userDetailsService.loadUserByUsername(request.getName());
		if (userDetail == null) {
			throw new UsernameNotFoundException("Please enter valid email");
		}

		this.doAuthenticate(request.getName(), request.getPassword());
		
		String token = this.jwtHelper.generateToken(userDetail);

		JwtResponse response = JwtResponse.builder().jwtToken(token).username(userDetail.getUsername()).build();

		User user = userService.findByEmail(request.getName());

//		if (user.isStatus() == false) {
//			throw new NotEnabledException("user not enabled");
//		}
		return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
	}

	private void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			authenticationManager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			throw new InvalidPasswordException("Invalid password please enter valid password!!");
		}
	}

	@PostMapping("/checkToken")

	public Boolean checkToken(@RequestParam String token) {
		try {
			System.out.println(token);
			return this.jwtHelper.isTokenExpired(token);
		} catch (ExpiredJwtException e) {
			return true;
		}
	}

	@GetMapping("/currentUser")

	public User getCurrentUser(Principal principal) {

		return userService.findByEmail(principal.getName());
	}

	@GetMapping("/currentRole")

	public String getCurrentRole(Principal principal) {

		User user= userService.findByEmail(principal.getName());
		
		Set<String> roles = user.getRoles();
		
		
		String role=null;
		for (String string : roles) {
			role=string;
		}
		 return role;
	}

}