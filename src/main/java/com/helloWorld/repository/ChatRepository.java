package com.helloWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helloWorld.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long>{

}
