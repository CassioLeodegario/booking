package com.hostfully.booking.resources.dto;

import com.hostfully.booking.enums.ReservationType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReservationDTO {

    private Long id;

    @NotNull(message = "Check-in date must be informed")
    private LocalDate checkIn;

    @NotNull(message = "Check-out date must be informed")
    private LocalDate checkOut;

    private ReservationType type;

    @NotNull(message = "User must be informed")
    @Min(value = 1, message =  "User must be informed")
    private Long userId;

    @NotNull(message = "Place must be informed")
    @Min(value = 1, message = "Place must be informed")
    private Long placeId;

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public ReservationType getType() {
        return type;
    }

    public void setType(ReservationType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
