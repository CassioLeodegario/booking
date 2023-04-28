package com.hostfully.booking.domain.repositories;

import com.hostfully.booking.domain.models.Reservation;
import com.hostfully.booking.enums.ReservationStatus;
import com.hostfully.booking.enums.ReservationType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    Optional<Reservation> getReservationByIdAndType(Long reservationId, ReservationType type);

    @Query("from Reservation r where r.id = :reservationId and r.userId = :userId")
    Optional<Reservation> getReservationByIdAndUser(long reservationId, long userId);

    @Query("from Reservation r where r.id = :reservationId and r.userId = :userId")
    List<Reservation> getReservationByUser(long userId);

    @Query("from Reservation r where r.status = :status " +
            "and (r.checkIn between :checkIn and :checkOut) " +
            "or (r.checkOut between :checkIn and :checkOut)")
    List<Reservation> getReservations(LocalDate checkIn, LocalDate checkOut, ReservationStatus status);
}
