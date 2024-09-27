package com.chatop.api.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FormCreateRentalDTO {
    private String name;
    private double surface;
    private double price;
    private MultipartFile picture;
    private String description;
    private Long ownerId;
}
