package com.chatop.api.service;

import com.chatop.api.dto.MessageDTO;
import com.chatop.api.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.api.model.Message;
import com.chatop.api.repository.MessageRepository;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class MessageService {
	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private MessageMapper messageMapper;
	
	public List<MessageDTO> getMessages() {
		List<Message> messages = messageRepository.findAll();
		return messages.stream().map(messageMapper::toDTO).collect(Collectors.toList());
	}

	public MessageDTO createMessage(MessageDTO messageDTO) {
		Message message = messageMapper.toEntity(messageDTO);
		Message savedMessage = messageRepository.save(message);
		return messageMapper.toDTO(savedMessage);
	}
}
