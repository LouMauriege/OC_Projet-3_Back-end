package com.chatop.api.mapper;

import com.chatop.api.dto.RentalDTO;
import com.chatop.api.model.Rental;
import com.chatop.api.model.User;
import com.chatop.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public RentalDTO toDTO(Rental rental) {
        return new RentalDTO(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription(),
                rental.getOwnerEntityId(),
                rental.getCreatedAt(),
                rental.getUpdatedAt()
        );
    }

    public Rental toEntity(RentalDTO rentalDTO) throws Exception {
        Rental rental = new Rental();
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setDescription(rentalDTO.getDescription());
        User existingUser = userMapper.toEntity(userService.getUserById(rentalDTO.getOwnerId()));
        rental.setOwnerId(existingUser);
        return rental;
    }
}
