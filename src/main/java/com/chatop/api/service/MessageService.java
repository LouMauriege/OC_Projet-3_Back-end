package com.chatop.api.service;

import com.chatop.api.dto.MessageDTO;
import com.chatop.api.mapper.MessageMapper;
import com.chatop.api.mapper.RentalMapper;
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
	
	public List<MessageDTO> getMessages() {
		List<Message> messages = messageRepository.findAll();
		return messages.stream().map(MessageMapper::toDTO).collect(Collectors.toList());
	}

	public MessageDTO createMessage(MessageDTO messageDTO) {
		Message message = MessageMapper.toEntity(messageDTO);
		Message savedMessage = messageRepository.save(message);
		return MessageMapper.toDTO(savedMessage);
	}
}
