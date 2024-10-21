package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private Long id;
    @JsonProperty("rental_id")
    private Long rentalId;
    @JsonProperty("user_id")
    private Long userId;
    private String message;

    @JsonFormat(pattern = "YYYY/MM/dd")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "YYYY/MM/dd")
    private LocalDateTime updatedAt;

    public MessageDTO(
            Long id,
            Long rentalId,
            Long userId,
            String message,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.rentalId = rentalId;
        this.userId = userId;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
