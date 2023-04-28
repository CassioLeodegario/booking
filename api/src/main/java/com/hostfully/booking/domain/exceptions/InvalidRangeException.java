package com.hostfully.booking.domain.exceptions;

public class InvalidRangeException extends RuntimeException {

    public InvalidRangeException(String message){
        super(message);
    }
}
