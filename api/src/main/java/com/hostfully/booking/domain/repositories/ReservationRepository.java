package com.hostfully.booking.domain.repositories;

import com.hostfully.booking.domain.models.Reservation;
import com.hostfully.booking.enums.ReservationStatus;
import com.hostfully.booking.enums.ReservationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    @Query("from Reservation r where r.status = :status and r.id = :reservationId and r.type = :type")
    Optional<Reservation> getReservationByIdTypeAndStatus(Long reservationId, ReservationType type, ReservationStatus status);

    @Query("from Reservation r where r.id = :reservationId and r.userId = :userId")
    Optional<Reservation> getReservationByIdAndUser(long reservationId, long userId);

    @Query("from Reservation r where r.userId = :userId and r.status = :status")
    Page<Reservation> getReservationByUser(long userId,  ReservationStatus status, Pageable pageable);

    @Query("from Reservation r where r.placeId = :placeId and  r.status = :status " +
            "and ((r.checkIn between :checkIn and :checkOut) " +
            "or (r.checkOut between :checkIn and :checkOut))")
    List<Reservation> getReservations(LocalDate checkIn, LocalDate checkOut, Long placeId, ReservationStatus status);

    @Query("from Reservation r where r.status = :status and r.placeId = :placeId")
    List<Reservation> getReservationsByPlaceAndStatus(Long placeId, ReservationStatus status);
}
