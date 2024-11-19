package com.chatop.api.controller;

import com.chatop.api.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chatop.api.service.MessageService;

@RestController
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@PostMapping("/messages")
	public ResponseEntity<String> createMessage (@RequestBody MessageDTO messageDTO) {
		MessageDTO createdMessage = messageService.createMessage(messageDTO);
		return ResponseEntity.ok("\"message\": \"Message send with success\"");
	}

}
