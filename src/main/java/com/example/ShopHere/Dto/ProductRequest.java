package com.example.ShopHere.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank
    private String name;

    @Positive
    private double price;

    @Min(value = 0)
    private int quantity;

    @NotNull
    private Long categoryId;

}
