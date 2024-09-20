package com.chatop.api.mapper;

import com.chatop.api.dto.MessageDTO;
import com.chatop.api.model.Message;

public class MessageMapper {
    public static MessageDTO toDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getRentalId(),
                message.getUserId(),
                message.getMessage(),
                message.getCreatedAt(),
                message.getUpdatedAt()
        );
    }

    public static Message toEntity(MessageDTO messageDto) {
        Message message = new Message();
        message.setRentalId(messageDto.getRentalId());
        message.setUserId(messageDto.getUserId());
        message.setMessage(messageDto.getMessage());
        return message;
    }
}
