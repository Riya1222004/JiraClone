package com.helloWorld.service;

import java.util.List;

import com.helloWorld.model.Messages;

public interface MessageService {
	Messages sendMessages(Long senderId,Long chatId,String content) throws Exception;
	List<Messages> getMessagesbyProjectId(Long projectId) throws Exception;
}
