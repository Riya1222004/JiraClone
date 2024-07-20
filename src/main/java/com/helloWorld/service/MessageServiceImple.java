package com.helloWorld.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helloWorld.model.Chat;
import com.helloWorld.model.Messages;
import com.helloWorld.model.User;
import com.helloWorld.repository.MessgeRepository;
import com.helloWorld.repository.UserRepository;

@Service
public class MessageServiceImple implements MessageService{

	@Autowired
	MessgeRepository messgeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProjectService projectService;
	
	@Override
	public Messages sendMessages(Long senderId, Long projectId, String content) throws Exception {
		
		User sender = userRepository.findById(senderId)
				.orElseThrow(() -> new Exception("User not found with id: " + senderId));
		
				Chat chat = projectService.getProjectById(projectId).getChat();
				
				Messages message = new Messages();
				message.setContent(content);
				message.setSender (sender);
				message.setCreatedAt(LocalDate.now());
				message.setChat(chat);
				Messages savedMessage=messgeRepository.save(message);
				
				chat.getMessage().add(savedMessage);	
				return savedMessage;
	}

	@Override
	public List<Messages> getMessagesbyProjectId(Long projectId) throws Exception {
		
		Chat chat = projectService.getChatByProjectId(projectId);
		List<Messages> findByChatIdOrderByCreatedAtAsc = messgeRepository.findByChatIdOrOrderByCreatedAtAsc(chat.getId());
		return findByChatIdOrderByCreatedAtAsc;
	}

}
