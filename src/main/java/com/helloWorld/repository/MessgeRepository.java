package com.helloWorld.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helloWorld.model.Messages;

public interface MessgeRepository extends JpaRepository<Messages, Long> {
	List<Messages> findByChatIdOrOrderByCreatedAtAsc(Long chatId);
}
