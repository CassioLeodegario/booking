package com.hostfully.booking.services;

import com.hostfully.booking.converters.ReservationConverter;
import com.hostfully.booking.domain.exceptions.InvalidRangeException;
import com.hostfully.booking.domain.exceptions.RangeNotAvailableException;
import com.hostfully.booking.domain.exceptions.ReservationNotFoundException;
import com.hostfully.booking.domain.models.Reservation;
import com.hostfully.booking.domain.repositories.ReservationRepository;
import com.hostfully.booking.enums.ReservationStatus;
import com.hostfully.booking.enums.ReservationType;
import com.hostfully.booking.resources.dto.ReservationDTO;
import com.hostfully.booking.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    Logger logger = LoggerFactory.getLogger(ReservationService.class);

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        logger.info("ReservationService.createReservation: Starting reservation creation");
        validate(reservationDTO);

        if (isAvailable(reservationDTO.getCheckIn(),
                reservationDTO.getCheckOut(),
                Optional.ofNullable(reservationDTO.getId()))) {
            reservationDTO.setStatus(ReservationStatus.ACTIVE);
            Reservation reservation = ReservationConverter.getReservationFromReservationDTO(reservationDTO);
            reservationRepository.save(reservation);

            logger.info("ReservationService.createReservation: Finishing reservation creation");
            return ReservationConverter.getReservationDTOFromReservation(reservation);
        }

        showUnavailability(reservationDTO);
        return null;
    }


    public ReservationDTO getReservation(Long reservationId, Long userId) {
        logger.info("ReservationService.getReservation: Retrieving reservation");

        Optional<Reservation> reservation = reservationRepository.getReservationByIdAndUser(reservationId, userId);

        logger.info("ReservationService.getReservation: Finishing reservation retrieval");
        return ReservationConverter.getReservationDTOFromReservation(
                reservation.orElseThrow(() ->
                        new ReservationNotFoundException("No booking found for given user")
                )
        );

    }

    public Page<ReservationDTO> getReservationsByUser(Long userId, Pageable pageable) {
        logger.info("ReservationService.getReservationsByUser: Retrieving reservation");

        Page<Reservation> reservations = reservationRepository.getReservationByUser(userId, pageable);

        if (!reservations.isEmpty()) {
            logger.info("ReservationService.getReservationsByUser: Finishing reservation retrieval");

            return reservations.map(ReservationConverter::getReservationDTOFromReservation);

        }

        throw new ReservationNotFoundException("No booking found for given user");
    }

    public void updateReservation(Long reservationId, ReservationDTO reservationDTO) {
        logger.info("ReservationService.updateReservation: updating reservation");
        validate(reservationDTO);

        Optional<Reservation> reservationOptional = reservationRepository
                .getReservationByIdTypeAndStatus(reservationId, ReservationType.BOOKING, ReservationStatus.ACTIVE);

        if (reservationOptional.isPresent()) {
            if (isAvailable(reservationDTO.getCheckIn(),
                    reservationDTO.getCheckOut(),
                    Optional.ofNullable(reservationId))) {
                Reservation reservation = reservationOptional.get();
                reservation.setCheckIn(reservationDTO.getCheckIn());
                reservation.setCheckOut(reservationDTO.getCheckOut());
                reservation.setPlaceId(reservationDTO.getPlaceId());
                reservationRepository.save(reservation);

                logger.info("ReservationService.updateReservation: Finishing reservation update");
                return;
            }

            showUnavailability(reservationDTO);

        }

        throw new ReservationNotFoundException("No booking found for given booking id");
    }

    public void deleteReservation(Long reservationId, ReservationType type) {
        logger.info("ReservationService.deleteReservation: deleting reservation");
        Optional<Reservation> reservationOptional = reservationRepository.getReservationByIdTypeAndStatus(reservationId, type, ReservationStatus.ACTIVE);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            reservation.setStatus(ReservationStatus.DELETED);
            reservationRepository.save(reservation);

            logger.info("ReservationService.deleteReservation: finishing reservation delete");
            return;
        }
        String typeDescription = type.toString().toLowerCase();
        throw new ReservationNotFoundException("No " + typeDescription + " found for given id");

    }

    private void showUnavailability(ReservationDTO reservationDTO) {
        List<String> unavailableDates = getUnavailableDates(
                reservationDTO.getCheckIn(),
                reservationDTO.getCheckOut(),
                Optional.ofNullable(reservationDTO.getId()));

        throw new RangeNotAvailableException(
                "The selected range is not available the dates "
                        + unavailableDates
                        + " are already taken"
        );
    }

    private List<String> getUnavailableDates(
            LocalDate checkIn,
            LocalDate checkOut,
            Optional<Long> reservationIdOptional) {

        List<String> dates = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.getReservations(checkIn, checkOut, ReservationStatus.ACTIVE);

        // remove current reservation from list so it is not considered occupied in case of update
        reservationIdOptional.ifPresent(reservationId -> reservations.removeIf(r -> reservationId.equals(r.getId())));

        for (Reservation reservation : reservations) {
            List<LocalDate> datesBetween = DateUtil.getDatesBetween(reservation.getCheckIn(), reservation.getCheckOut());

            datesBetween.removeIf(currentDate -> currentDate.isBefore(checkIn) || currentDate.isAfter(checkOut));

            dates.addAll(datesBetween.stream()
                    .map(LocalDate::toString)
                    .collect(Collectors.toList()));
        }

        return dates;
    }

    private boolean isAvailable(LocalDate checkIn, LocalDate checkOut, Optional<Long> reservationIdOptional) {

        List<Reservation> reservations = reservationRepository.getReservations(checkIn, checkOut, ReservationStatus.ACTIVE);
        // remove current reservation from list so it is not considered occupied in case of update
        reservationIdOptional.ifPresent(reservationId -> reservations.removeIf(r -> reservationId.equals(r.getId())));

        return reservations.isEmpty();
    }

    private void validate(ReservationDTO reservationDTO) {
        if (reservationDTO.getCheckIn().isAfter(reservationDTO.getCheckOut())) {
            throw new InvalidRangeException("Check-in date should not be after check-out date");
        }

        if (reservationDTO.getCheckIn().isBefore(LocalDate.now())) {
            throw new InvalidRangeException("Check-in date should not be a past date");
        }
    }

}
