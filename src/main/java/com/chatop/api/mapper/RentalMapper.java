package com.chatop.api.mapper;

import com.chatop.api.dto.RentalDTO;
import com.chatop.api.exception.UserNotFound;
import com.chatop.api.model.Rental;
import com.chatop.api.model.User;
import com.chatop.api.repository.UserRepository;
import com.chatop.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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

    public Rental toEntity(RentalDTO rentalDTO) {
        User ownerId = userRepository.findById(rentalDTO.getId()).orElseThrow(() -> new UserNotFound("Utilisateur non trouvé !"));
        Rental rental = new Rental();
        rental.setOwnerId(ownerId);
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setDescription(rentalDTO.getDescription());
        return rental;
    }
}
