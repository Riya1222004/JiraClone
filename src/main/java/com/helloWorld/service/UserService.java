package com.helloWorld.service;

import com.helloWorld.model.User;

public interface UserService {
	User findUserProfileByJWT(String jwt) throws Exception;
	
	User findUserByEmail(String email) throws Exception;
	
	User findUserById(Long userId) throws Exception;
	
	User updateUsersProjectSize(User user , int number);
}
