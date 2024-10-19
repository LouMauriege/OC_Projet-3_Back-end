package com.chatop.api.mapper;

import com.chatop.api.dto.MessageDTO;
import com.chatop.api.dto.RentalDTO;
import com.chatop.api.dto.UserDTO;
import com.chatop.api.exception.RentalNotFound;
import com.chatop.api.exception.UserNotFound;
import com.chatop.api.model.Message;
import com.chatop.api.model.Rental;
import com.chatop.api.model.User;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.repository.UserRepository;
import com.chatop.api.service.RentalService;
import com.chatop.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MessageMapper {
    @Autowired
    private UserService userService;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

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

    public Message toEntity(MessageDTO messageDto) {
        Rental rentalFind = rentalRepository.findById(messageDto.getRentalId()).orElseThrow(
                () -> new RentalNotFound("Rental non trouvé !"));
        User userFind = userRepository.findById(messageDto.getUserId()).orElseThrow(
                () -> new UserNotFound("Utilisateur non trouvé !"));
        Message message = new Message();
        message.setRentalId(rentalFind);
        message.setUserId(userFind);
        message.setMessage(messageDto.getMessage());
        return message;
    }
}
