package com.chatop.api.model;

import lombok.Data;

@Data
public class FormUpdateRental {
    private String name;
    private double surface;
    private double price;
    private String description;
}
