package com.hostfully.booking.mocks;

import com.hostfully.booking.domain.models.Reservation;
import com.hostfully.booking.enums.ReservationType;
import com.hostfully.booking.resources.dto.ReservationDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class CommonMocks {

    public static ReservationDTO mockReservationDTO(Long id, ReservationType type){
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(id);
        reservationDTO.setPlaceId(2L);
        reservationDTO.setUserId(3L);
        reservationDTO.setCheckIn(LocalDate.now().plusDays(1));
        reservationDTO.setCheckOut(LocalDate.now().plusDays(10));
        reservationDTO.setType(type);
        return reservationDTO;
    }

    public static Reservation mockReservation(Long id, ReservationType type){
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setPlaceId(2L);
        reservation.setUserId(3L);
        reservation.setCheckIn(LocalDate.now().plusDays(1));
        reservation.setCheckOut(LocalDate.now().plusDays(10));
        reservation.setType(type);
        return reservation;
    }

    public static List<Reservation> mockReservationList(){
        List<Reservation> reservations = new ArrayList<>();

        LongStream.range(0,5).forEachOrdered(n -> {
            reservations.add(mockReservation(n, ReservationType.BLOCK));
        });

        return reservations;
    }

}
