package com.hostfully.booking.domain.exceptions;

public class RangeNotAvailableException extends RuntimeException {

    public RangeNotAvailableException(String message){
        super(message);
    }
}
