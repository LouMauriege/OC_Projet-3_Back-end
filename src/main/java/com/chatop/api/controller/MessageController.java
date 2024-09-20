package com.chatop.api.controller;

import com.chatop.api.dto.MessageDTO;
import com.chatop.api.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chatop.api.service.MessageService;

import java.util.List;

@RestController
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@GetMapping("/messages")
	public ResponseEntity<List<MessageDTO>> getMessages() {
		List<MessageDTO> messages = messageService.getMessages();
		return ResponseEntity.ok(messages);
	}

	@PostMapping("/messages")
	public String createMessage (@RequestBody MessageDTO messageDTO) {
		MessageDTO createdMessage = messageService.createMessage(messageDTO);
		return "Message send with success";
	}

}
