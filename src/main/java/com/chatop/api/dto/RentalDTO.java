package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.nio.file.Path;
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

    @JsonFormat(pattern = "YYYY/MM/dd")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "YYYY/MM/dd")
    private LocalDateTime updatedAt;

    public RentalDTO(
            Long id,
            String name,
            double surface,
            double price,
            String picture,
            String description,

            @JsonProperty("owner_id")
            Long ownerId,

            @JsonProperty("created_at")
            LocalDateTime createdAt,

            @JsonProperty("updated_at")
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

    public RentalDTO(
            String name,
            double surface,
            double price,
            String description,
            Long ownerId
    ) {
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.description = description;
        this.ownerId = ownerId;
    }

    public RentalDTO(
            String name,
            double surface,
            double price,
            String description
    ) {
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.description = description;
    }

    public RentalDTO() {};
}
