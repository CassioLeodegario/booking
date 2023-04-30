package com.hostfully.booking.resources;

import com.hostfully.booking.config.CustomPage;
import com.hostfully.booking.enums.ReservationType;
import com.hostfully.booking.resources.dto.ReservationDTO;
import com.hostfully.booking.services.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Reservations")
@RestController
@RequestMapping("/v1")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ApiOperation("Create a new booking")
    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking(
            @RequestBody @Validated  ReservationDTO reservationDTO
    ) {
        reservationDTO.setType(ReservationType.BOOKING);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservationService.createReservation(reservationDTO));
    }

    @ApiOperation("Get a single booking")
    @GetMapping("/bookings/{userId}/{bookingId}")
    public ResponseEntity<?> getBooking(
            @PathVariable Long bookingId,
            @PathVariable Long userId){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservationService.getReservation(bookingId, userId));
    }

    @ApiOperation("Get all bookings for a user")
    @GetMapping(value = "/bookings")
    public ResponseEntity<CustomPage<ReservationDTO>> getAllBookings(
            @PageableDefault(page = 0, size = 10, sort = "checkIn", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam Long userId
    ){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CustomPage<>(reservationService.getReservationsByUser(userId, pageable)));
    }

    @ApiOperation("Update a booking")
    @PutMapping("/bookings/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBooking(
            @RequestBody @Validated  ReservationDTO reservationDTO,
            @PathVariable(value = "bookingId") Long bookingId
    ) {
        reservationService.updateReservation(bookingId, reservationDTO);
    }

    @ApiOperation("Delete a booking")
    @DeleteMapping("/bookings/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBooking(
            @PathVariable(value = "bookingId") Long bookingId
    ) {
        reservationService.deleteReservation(bookingId, ReservationType.BOOKING);
    }

    @ApiOperation("Create a block")
    @PostMapping("/blocks")
    public ResponseEntity<?> createBlock(
            @RequestBody @Validated  ReservationDTO reservationDTO
    ) {
        reservationDTO.setType(ReservationType.BLOCK);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservationService.createReservation(reservationDTO));
    }

    @ApiOperation("Remove a block")
    @DeleteMapping("/blocks/{blockId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBlock(
            @PathVariable(value = "blockId") Long blockId
    ) {
        reservationService.deleteReservation(blockId, ReservationType.BLOCK);
    }


}
