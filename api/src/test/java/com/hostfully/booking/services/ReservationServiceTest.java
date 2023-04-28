package com.hostfully.booking.services;

import com.hostfully.booking.domain.exceptions.InvalidRangeException;
import com.hostfully.booking.domain.exceptions.RangeNotAvailableException;
import com.hostfully.booking.domain.exceptions.ReservationNotFoundException;
import com.hostfully.booking.domain.models.Reservation;
import com.hostfully.booking.domain.repositories.ReservationRepository;
import com.hostfully.booking.enums.ReservationStatus;
import com.hostfully.booking.enums.ReservationType;
import com.hostfully.booking.resources.dto.ReservationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static com.hostfully.booking.mocks.CommonMocks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    ReservationRepository reservationRepository;

    @Test
    void createReservation(){
        ReservationDTO reservationDTO = mockReservationDTO(null, ReservationType.BOOKING);

        when(reservationRepository.getReservations(
                any(LocalDate.class),
                any(LocalDate.class),
                eq(ReservationStatus.ACTIVE)))
                .thenReturn(Collections.emptyList());

        when(reservationRepository.save(
                any(Reservation.class)))
                .thenReturn(mockReservation(1L, ReservationType.BOOKING));

        ReservationDTO result = reservationService.createReservation(reservationDTO);

        assertThat(result.getId()).isNotNull();
    }

    @Test
    void createReservationRangeTaken(){
        ReservationDTO reservationDTO = mockReservationDTO(1L, ReservationType.BOOKING);

        when(reservationRepository.getReservations(
                any(LocalDate.class),
                any(LocalDate.class),
                eq(ReservationStatus.ACTIVE)))
                .thenReturn(mockReservationList());


        Exception result =  assertThrows(RangeNotAvailableException.class,
                () -> reservationService.createReservation(reservationDTO));

        assertThat(result.getMessage()).contains("The selected range is not available");
    }

    @Test
    void createReservationCheckinAfterCheckout(){
        ReservationDTO reservationDTO = mockReservationDTO(1L, ReservationType.BOOKING);
        reservationDTO.setCheckIn(reservationDTO.getCheckOut().plusDays(1));

        Exception result =  assertThrows(InvalidRangeException.class,
                () -> reservationService.createReservation(reservationDTO));

        assertThat(result.getMessage()).contains("Check-in date should not be after check-out date");
    }

    @Test
    void createReservationCheckinIsPastDate(){
        ReservationDTO reservationDTO = mockReservationDTO(1L, ReservationType.BOOKING);
        reservationDTO.setCheckIn(LocalDate.now().minusDays(5));

        Exception result =  assertThrows(InvalidRangeException.class,
                () -> reservationService.createReservation(reservationDTO));

        assertThat(result.getMessage()).contains("Check-in date should not be a past date");
    }

    @Test
    void getReservation(){
        long reservationId = 5L;
        long userId = 3L;

        Reservation mockReservation = mockReservation(reservationId, ReservationType.BOOKING);

        when(reservationRepository.getReservationByIdAndUser(
                anyLong(),
                anyLong()))
                .thenReturn(Optional.of(mockReservation));

        ReservationDTO result = reservationService.getReservation(reservationId, userId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(mockReservation.getId());
        assertThat(result.getCheckIn()).isEqualTo(mockReservation.getCheckIn());
        assertThat(result.getCheckOut()).isEqualTo(mockReservation.getCheckOut());
        assertThat(result.getPlaceId()).isEqualTo(mockReservation.getPlaceId());
        assertThat(result.getType()).isEqualTo(mockReservation.getType());

    }

    @Test
    void getReservationNotFound(){
        long reservationId = 5L;
        long userId = 3L;

        when(reservationRepository.getReservationByIdAndUser(
                anyLong(),
                anyLong()))
                .thenReturn(Optional.empty());

        Exception result =  assertThrows(ReservationNotFoundException.class,
                () -> reservationService.getReservation(reservationId, userId));

        assertThat(result.getMessage()).contains("No booking found for given user");

    }

    @Test
    void updateReservation(){
        Long reservationId = 2L;
        ReservationDTO reservationDTO = mockReservationDTO(reservationId, ReservationType.BOOKING);

        when(reservationRepository.getReservations(
                any(LocalDate.class),
                any(LocalDate.class),
                eq(ReservationStatus.ACTIVE)))
                .thenReturn(Collections.emptyList());

        when(reservationRepository.findById(anyLong()))
                .thenReturn(Optional.of(mockReservation(reservationId, ReservationType.BOOKING)));

        when(reservationRepository.save(any(Reservation.class)))
                .thenReturn(mockReservation(1L, ReservationType.BOOKING));

        reservationService.updateReservation(reservationId, reservationDTO);

        ArgumentCaptor<Reservation> reservationCaptor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationRepository, times(1)).save(reservationCaptor.capture());

        assertThat(reservationCaptor.getValue()).isNotNull();
        assertThat(reservationCaptor.getValue().getId()).isEqualTo(reservationId);
        assertThat(reservationCaptor.getValue().getCheckIn()).isEqualTo(reservationDTO.getCheckIn());
        assertThat(reservationCaptor.getValue().getCheckOut()).isEqualTo(reservationDTO.getCheckOut());

    }

    @Test
    void updateReservationNotFound(){
        Long reservationId = 2L;
        String typeLowerCase = ReservationType.BOOKING.toString().toLowerCase();
        ReservationDTO reservationDTO = mockReservationDTO(reservationId, ReservationType.BOOKING);

        when(reservationRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Exception result =  assertThrows(ReservationNotFoundException.class,
                () -> reservationService.updateReservation(reservationId, reservationDTO));

        assertThat(result.getMessage()).contains("No "+typeLowerCase+" found for given "+typeLowerCase+" id");
    }

    @Test
    void updateReservationRangeTaken(){
        Long reservationId = 6L;
        ReservationDTO reservationDTO = mockReservationDTO(reservationId, ReservationType.BOOKING);

        when(reservationRepository.getReservations(
                any(LocalDate.class),
                any(LocalDate.class),
                eq(ReservationStatus.ACTIVE)))
                .thenReturn(mockReservationList());

        when(reservationRepository.findById(anyLong()))
                .thenReturn(Optional.of(mockReservation(reservationId, ReservationType.BOOKING)));

        Exception result =  assertThrows(RangeNotAvailableException.class,
                () -> reservationService.updateReservation(reservationId, reservationDTO));

        assertThat(result.getMessage()).contains("The selected range is not available");
    }

    @Test
    void updateReservationCheckinAfterCheckout(){
        Long reservationId = 2L;
        ReservationDTO reservationDTO = mockReservationDTO(reservationId, ReservationType.BOOKING);
        reservationDTO.setCheckIn(reservationDTO.getCheckOut().plusDays(1));

        Exception result =  assertThrows(InvalidRangeException.class,
                () -> reservationService.updateReservation(reservationId, reservationDTO));

        assertThat(result.getMessage()).contains("Check-in date should not be after check-out date");
    }

    @Test
    void updateReservationCheckinIsPastDate(){
        Long reservationId = 2L;
        ReservationDTO reservationDTO = mockReservationDTO(reservationId, ReservationType.BOOKING);
        reservationDTO.setCheckIn(LocalDate.now().minusDays(5));

        Exception result =  assertThrows(InvalidRangeException.class,
                () -> reservationService.updateReservation(reservationId, reservationDTO));

        assertThat(result.getMessage()).contains("Check-in date should not be a past date");
    }

    @Test
    void deleteReservation(){
        Long reservationId = 2L;

        when(reservationRepository.getReservationByIdAndType(anyLong(), eq(ReservationType.BOOKING)))
                .thenReturn(Optional.of(mockReservation(reservationId, ReservationType.BOOKING)));

        when(reservationRepository.save(any(Reservation.class)))
                .thenReturn(mockReservation(1L, ReservationType.BOOKING));

        reservationService.deleteReservation(reservationId, ReservationType.BOOKING);

        ArgumentCaptor<Reservation> reservationCaptor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationRepository, times(1)).save(reservationCaptor.capture());

        assertThat(reservationCaptor.getValue()).isNotNull();
        assertThat(reservationCaptor.getValue().getId()).isEqualTo(reservationId);
        assertThat(reservationCaptor.getValue().getStatus()).isEqualTo(ReservationStatus.DELETED);

    }

    @Test
    void deleteReservationNotFound(){
        Long reservationId = 2L;
        String typeLowerCase = ReservationType.BLOCK.toString().toLowerCase();

        when(reservationRepository.getReservationByIdAndType(anyLong(), eq(ReservationType.BLOCK)))
                .thenReturn(Optional.empty());

        Exception result =  assertThrows(ReservationNotFoundException.class,
                () -> reservationService.deleteReservation(reservationId, ReservationType.BLOCK));

        assertThat(result.getMessage()).contains("No "+typeLowerCase+" found for given id");
    }

}
