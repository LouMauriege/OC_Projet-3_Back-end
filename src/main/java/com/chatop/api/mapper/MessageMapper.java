package com.chatop.api.mapper;

import com.chatop.api.dto.MessageDTO;
import com.chatop.api.model.Message;
import com.chatop.api.model.Rental;
import com.chatop.api.model.User;
import com.chatop.api.service.RentalService;
import com.chatop.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    @Autowired
    private UserService userService;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RentalMapper rentalMapper;

    public MessageDTO toDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getRentalId(),
                message.getUserId(),
                message.getMessage(),
                message.getCreatedAt(),
                message.getUpdatedAt()
        );
    }

    public Message toEntity(MessageDTO messageDto) throws Exception {
        Message message = new Message();
        Rental existingRental = rentalMapper.toEntity(rentalService.getRentalById(messageDto.getRentalId()));
        message.setRentalId(existingRental);
        User existingUser = userMapper.toEntity(userService.getUserById(messageDto.getUserId()));
        message.setUserId(existingUser);
        message.setMessage(messageDto.getMessage());
        return message;
    }
}
