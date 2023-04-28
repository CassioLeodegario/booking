package com.hostfully.booking.resources;

import com.hostfully.booking.enums.ReservationType;
import com.hostfully.booking.resources.dto.ReservationDTO;
import com.hostfully.booking.services.ReservationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking(
            @RequestBody @Validated  ReservationDTO reservationDTO
    ) {
        reservationDTO.setType(ReservationType.BOOKING);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservationService.createReservation(reservationDTO));
    }

    @GetMapping("/bookings/{userId}/{bookingId}")
    public ResponseEntity<?> getReservation(
            @PathVariable Long bookingId,
            @PathVariable Long userId){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservationService.getReservation(bookingId, userId));
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<ReservationDTO>> getAllReservations(
            @PageableDefault(page = 0, size = 10, sort = "checkIn", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam Long userId
    ){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservationService.getReservationsByUser(userId));
    }

    @PutMapping("/bookings/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBooking(
            @RequestBody @Validated  ReservationDTO reservationDTO,
            @PathVariable(value = "bookingId") Long bookingId
    ) {
        reservationService.updateReservation(bookingId, reservationDTO);
    }

    @DeleteMapping("/bookings/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReservation(
            @PathVariable(value = "bookingId") Long bookingId
    ) {
        reservationService.deleteReservation(bookingId, ReservationType.BOOKING);
    }

    @PostMapping("/blocks")
    public ResponseEntity<?> createBlock(
            @RequestBody @Validated  ReservationDTO reservationDTO
    ) {
        reservationDTO.setType(ReservationType.BLOCK);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservationService.createReservation(reservationDTO));
    }

    @DeleteMapping("/blocks/{blockId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBlock(
            @PathVariable(value = "blockId") Long blockId
    ) {
        reservationService.deleteReservation(blockId, ReservationType.BLOCK);
    }


}
