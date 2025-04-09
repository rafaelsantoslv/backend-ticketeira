package com.unyx.ticketeira.exception;

public class InvalidCredentialsException  extends  RuntimeException{
    public InvalidCredentialsException(String message) {
            super(message);
    }
}
