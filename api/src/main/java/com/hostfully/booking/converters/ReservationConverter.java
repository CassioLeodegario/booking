package com.hostfully.booking.converters;

import com.hostfully.booking.domain.models.Reservation;
import com.hostfully.booking.resources.dto.ReservationDTO;

public class ReservationConverter {
    public static Reservation getReservationFromReservationDTO(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setPlaceId(reservationDTO.getPlaceId());
        reservation.setUserId(reservationDTO.getUserId());
        reservation.setCheckIn(reservationDTO.getCheckIn());
        reservation.setCheckOut(reservationDTO.getCheckOut());
        reservation.setType(reservationDTO.getType());
        reservation.setStatus(reservationDTO.getStatus());
        return reservation;
    }

    public static ReservationDTO getReservationDTOFromReservation(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setPlaceId(reservation.getPlaceId());
        reservationDTO.setUserId(reservation.getUserId());
        reservationDTO.setCheckIn(reservation.getCheckIn());
        reservationDTO.setCheckOut(reservation.getCheckOut());
        reservationDTO.setType(reservation.getType());
        reservationDTO.setStatus(reservation.getStatus());
        return reservationDTO;
    }
}
