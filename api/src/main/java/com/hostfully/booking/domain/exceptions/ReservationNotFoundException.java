package com.hostfully.booking.domain.exceptions;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String message){
        super(message);
    }
}
