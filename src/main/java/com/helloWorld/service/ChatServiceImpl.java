package com.helloWorld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helloWorld.model.Chat;
import com.helloWorld.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService{
	@Autowired
	private  ChatRepository chatRepository;
	@Override
	public Chat createChat(Chat chat) {
		// TODO Auto-generated method stub
		return chatRepository.save(chat);
	}

}
