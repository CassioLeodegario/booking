package com.hostfully.booking.resources;

import com.hostfully.booking.services.PlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(tags = "Places")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @ApiOperation("List unavailable dates for a place")
    @GetMapping("/{placeId}/unavailableDates")
    public ResponseEntity<?> getUnavailableDates(
            @PathVariable Long placeId){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeService.getDatesTaken(placeId, Optional.empty()));
    }

    @ApiOperation("List unavailable dates for a place considering current booking")
    @GetMapping("/{placeId}/unavailableDates/{bookingId}")
    public ResponseEntity<?> getUnavailableDatesPerBooking(
            @PathVariable Long placeId,
            @PathVariable Long bookingId){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeService.getDatesTaken(placeId,  Optional.ofNullable(bookingId)));
    }

}
