package com.example.ShopHere.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long id;
    private String name ;
    private Double price;
    private int quantity;
    private Long categoryId;

}
