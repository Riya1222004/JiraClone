package com.helloWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helloWorld.model.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

}
