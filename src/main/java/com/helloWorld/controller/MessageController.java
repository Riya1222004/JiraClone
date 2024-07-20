package com.helloWorld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helloWorld.model.Chat;
import com.helloWorld.model.Messages;
import com.helloWorld.model.User;
import com.helloWorld.request.MessageRequest;
import com.helloWorld.service.MessageService;
import com.helloWorld.service.ProjectService;
import com.helloWorld.service.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	@Autowired 
	private MessageService messageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@PostMapping("/send")
	public ResponseEntity<Messages> sendMessage (@RequestBody MessageRequest request) throws Exception {
		User user = userService.findUserById(request.getSenderId());
		if(user==null) throw new Exception("user Not found with id "+request.getSenderId());
		
		Chat chats = projectService.getProjectById(request.getProjectId()).getChat(); 
		
		if (chats==null) throw new Exception("Chats not found");
		Messages sentMessage = messageService.sendMessages(request.getSenderId(),request.getProjectId(), request.getContent());
		return ResponseEntity.ok (sentMessage);
	}
	
	@GetMapping("/chat/{projectId}")
	public ResponseEntity<List<Messages>> getMessagebyChtaId(@PathVariable Long projectId) throws Exception{
		List<Messages> messages=messageService.getMessagesbyProjectId(projectId);
		return ResponseEntity.ok(messages);
	}
}