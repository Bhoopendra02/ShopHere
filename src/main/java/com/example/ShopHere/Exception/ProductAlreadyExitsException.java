package com.example.ShopHere.Exception;

public class ProductAlreadyExitsException extends RuntimeException{


    public ProductAlreadyExitsException(String message){
        super(message);
    }
}
