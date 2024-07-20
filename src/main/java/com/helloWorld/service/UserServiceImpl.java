package com.helloWorld.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helloWorld.config.JWTProvider;
import com.helloWorld.model.User;
import com.helloWorld.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	@Override
	public User findUserProfileByJWT(String jwt) throws Exception {
		// TODO Auto-generated method stub
		String email=JWTProvider.getEmailFromToken(jwt);
		return findUserByEmail(email);
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		User user=userRepository.findByEmail(email);
		if(user==null)
			throw new Exception("User Not Found");
		return user;
	}

	@Override
	public User findUserById(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Optional<User> optionalUser=userRepository.findById(userId);
		if(optionalUser==null)
			throw new Exception("User Not Found");
		return optionalUser.get();
	}

	@Override
	public User updateUsersProjectSize(User user, int number) {
		// TODO Auto-generated method stub
		user.setProjectSize(user.getProjectSize()+number);
		
		return userRepository.save(user);
	}

}
