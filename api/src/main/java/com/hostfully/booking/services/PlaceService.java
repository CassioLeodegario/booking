package com.hostfully.booking.services;

import com.hostfully.booking.domain.models.Reservation;
import com.hostfully.booking.domain.repositories.ReservationRepository;
import com.hostfully.booking.enums.ReservationStatus;
import com.hostfully.booking.util.DateUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private final ReservationRepository reservationRepository;

    public PlaceService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<LocalDate> getDatesTaken(long placeId, Optional<Long> bookingId){
        List<LocalDate> dates = new ArrayList<>();
        List<Reservation> reservations = reservationRepository
                .getReservationsByPlaceAndStatus(placeId, ReservationStatus.ACTIVE);

        bookingId.ifPresent(id -> reservations.removeIf(r -> id.equals(r.getId())));

        for (Reservation reservation : reservations) {
            List<LocalDate> datesBetween = DateUtil.getDatesBetween(reservation.getCheckIn(), reservation.getCheckOut());
            dates.addAll(datesBetween);
        }

        return dates;
    }
}
