package com.example.ShopHere.Exception;

public class InvalidCredentialsException extends  RuntimeException{

    public InvalidCredentialsException(String message){
        super(message);
    }
}
