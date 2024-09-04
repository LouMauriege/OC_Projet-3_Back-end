package com.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.model.Message;
import com.chatop.api.service.MessageService;

@RestController
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@GetMapping("/messages")
	public Iterable<Message> getMessages() {
		return messageService.getMessages();
	}

}
