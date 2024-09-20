package com.chatop.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentalDTO {
    private Long id;
    private String name;
    private double surface;
    private double price;
    private String picture;
    private String description;
    private Long ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RentalDTO(
            Long id,
            String name,
            double surface,
            double price,
            String picture,
            String description,
            Long ownerId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
