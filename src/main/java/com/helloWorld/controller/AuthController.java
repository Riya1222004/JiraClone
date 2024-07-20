package com.helloWorld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helloWorld.config.JWTProvider;
import com.helloWorld.model.User;
import com.helloWorld.repository.UserRepository;
import com.helloWorld.request.LoginRequest;
import com.helloWorld.response.AuthResponse;
import com.helloWorld.service.CustomUserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomUserServiceImpl customUserServiceImpl;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception{
		User isExsits=userRepository.findByEmail(user.getEmail());
		if(isExsits!=null) {
			throw new Exception("Email exists");
		}
		User createdUser=new User();
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
		createdUser.setFullName(user.getFullName());
		createdUser.setEmail(user.getEmail());
		
		User savedUser=userRepository.save(createdUser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt=JWTProvider.genrateToken(authentication);
		
		AuthResponse response=new AuthResponse();
		response.setMessage("SignUp Success");
		response.setJwt(jwt);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
		
	}
	
	@PostMapping("/signing")
	public ResponseEntity<AuthResponse> signing(@RequestBody LoginRequest loginRequest){
		String username=loginRequest.getEmail();
		String password=loginRequest.getPassword();
		Authentication authentication=authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt=JWTProvider.genrateToken(authentication);
		
		AuthResponse response=new AuthResponse();
		response.setMessage("SignIn Success");
		response.setJwt(jwt);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

	private Authentication authenticate(String username, String password) {
		// TODO Auto-generated method stub
		UserDetails userDetails=customUserServiceImpl.loadUserByUsername(username);
		if(userDetails==null) {
			throw new BadCredentialsException("Invalid Username");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
}

