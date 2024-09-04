package com.chatop.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.api.model.Message;
import com.chatop.api.repository.MessageRepository;

import lombok.Data;

@Data
@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	public Iterable<Message> getMessages() {
		return messageRepository.findAll();
	}
}
