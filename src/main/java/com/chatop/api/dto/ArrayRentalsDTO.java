package com.chatop.api.dto;

import lombok.Data;

@Data
public class ArrayRentalsDTO {
    private RentalDTO[] rentals;

    public ArrayRentalsDTO(RentalDTO[] arrayOfRentalsDTO) {
        this.rentals = arrayOfRentalsDTO;
    }
}
