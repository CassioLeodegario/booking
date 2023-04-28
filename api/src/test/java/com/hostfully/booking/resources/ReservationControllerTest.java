package com.hostfully.booking.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hostfully.booking.domain.exceptions.InvalidRangeException;
import com.hostfully.booking.domain.exceptions.RangeNotAvailableException;
import com.hostfully.booking.domain.exceptions.ReservationNotFoundException;
import com.hostfully.booking.enums.ReservationType;
import com.hostfully.booking.resources.dto.ReservationDTO;
import com.hostfully.booking.services.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static com.hostfully.booking.mocks.CommonMocks.mockReservationDTO;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;


    @Test
    public void createBooking() throws Exception {

        ReservationDTO reservationDTO = mockReservationDTO(null, ReservationType.BOOKING);

        when(reservationService.createReservation(any(ReservationDTO.class))).thenReturn(mockReservationDTO(1L, ReservationType.BOOKING));


        RequestBuilder request = MockMvcRequestBuilders
                .post("/v1/bookings")
                .content(asJsonString(reservationDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        String result = asJsonString(mockReservationDTO(1L, ReservationType.BOOKING));

        mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(result))
                .andReturn();

    }

    @Test
    public void createBookingValidationError() throws Exception {

        ReservationDTO reservationDTO = mockReservationDTO(null, ReservationType.BOOKING);
        reservationDTO.setCheckIn(null);

        when(reservationService.createReservation(any(ReservationDTO.class))).thenReturn(mockReservationDTO(1L, ReservationType.BOOKING));


        RequestBuilder request = MockMvcRequestBuilders
                .post("/v1/bookings")
                .content(asJsonString(reservationDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);


        mockMvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("\"messages\":[\"Check-in date must be informed\"]")))
                .andReturn();

    }

    @Test
    public void createBookingRangeNotAvailable() throws Exception {
        String errorMessage = "The selected range is not available";
        ReservationDTO reservationDTO = mockReservationDTO(null, ReservationType.BOOKING);

        when(reservationService.createReservation(any(ReservationDTO.class)))
                .thenThrow(new RangeNotAvailableException(errorMessage));


        RequestBuilder request = MockMvcRequestBuilders
                .post("/v1/bookings")
                .content(asJsonString(reservationDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);


        mockMvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(errorMessage)))
                .andReturn();

    }

    @Test
    public void createBookingInvalidRange() throws Exception {
        String errorMessage = "Check-in date should not be a past date";
        ReservationDTO reservationDTO = mockReservationDTO(null, ReservationType.BOOKING);
        reservationDTO.setCheckIn(LocalDate.now().minusDays(6));


        when(reservationService.createReservation(any(ReservationDTO.class)))
                .thenThrow(new InvalidRangeException(errorMessage));


        RequestBuilder request = MockMvcRequestBuilders
                .post("/v1/bookings")
                .content(asJsonString(reservationDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);


        mockMvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(errorMessage)))
                .andReturn();

    }

    @Test
    public void getReservation() throws Exception {
        final long userId = 5L;
        final long reservationId = 10L;

        when(reservationService.getReservation(anyLong(), anyLong()))
                .thenReturn(mockReservationDTO(reservationId, ReservationType.BOOKING));


        RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/bookings/"+userId+"/"+reservationId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        String result = asJsonString(mockReservationDTO(reservationId, ReservationType.BOOKING));

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(result))
                .andReturn();

    }

    @Test
    public void getReservationNotFound() throws Exception {
        final long userId = 5L;
        final long reservationId = 10L;

        when(reservationService.getReservation(anyLong(), anyLong()))
                .thenThrow(new ReservationNotFoundException("No booking found for given user"));


        RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/bookings/"+userId+"/"+reservationId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        String result = asJsonString(mockReservationDTO(reservationId, ReservationType.BOOKING));

        mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No booking found for given user")))
                .andReturn();

    }

    @Test
    public void updateBooking() throws Exception {
        long bookingId = 3L;

        ReservationDTO reservationDTO = mockReservationDTO(bookingId, ReservationType.BOOKING);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/v1/bookings/"+bookingId)
                .content(asJsonString(reservationDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void updateBookingReservationNotFound() throws Exception {
        long bookingId = 3L;
        String errorMessage = "No booking found for given booking id";

        ReservationDTO reservationDTO = mockReservationDTO(bookingId, ReservationType.BOOKING);

        doThrow(new ReservationNotFoundException(errorMessage))
                .when(reservationService)
                .updateReservation(anyLong(), any(ReservationDTO.class));

        RequestBuilder request = MockMvcRequestBuilders
                .put("/v1/bookings/"+bookingId)
                .content(asJsonString(reservationDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(errorMessage)))
                .andReturn();

    }

    @Test
    public void deleteBooking() throws Exception {
        long bookingId = 3L;

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/v1/bookings/"+bookingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void deleteBookingReservationNotFound() throws Exception {
        long bookingId = 3L;
        String errorMessage = "No booking found for given booking id";

        doThrow(new ReservationNotFoundException(errorMessage))
                .when(reservationService)
                .deleteReservation(anyLong(), eq(ReservationType.BOOKING));

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/v1/bookings/"+bookingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(errorMessage)))
                .andReturn();

    }

    @Test
    public void createBlock() throws Exception {

        ReservationDTO reservationDTO = mockReservationDTO(null, ReservationType.BLOCK);

        when(reservationService.createReservation(any(ReservationDTO.class))).thenReturn(mockReservationDTO(1L, ReservationType.BLOCK));


        RequestBuilder request = MockMvcRequestBuilders
                .post("/v1/blocks")
                .content(asJsonString(reservationDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        String result = asJsonString(mockReservationDTO(1L, ReservationType.BLOCK));

        mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(result))
                .andReturn();

    }

    @Test
    public void createBlockValidationError() throws Exception {

        ReservationDTO reservationDTO = mockReservationDTO(null, ReservationType.BLOCK);
        reservationDTO.setPlaceId(null);

        when(reservationService.createReservation(any(ReservationDTO.class))).thenReturn(mockReservationDTO(1L, ReservationType.BLOCK));


        RequestBuilder request = MockMvcRequestBuilders
                .post("/v1/blocks")
                .content(asJsonString(reservationDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);


        mockMvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("\"messages\":[\"Place must be informed\"]")))
                .andReturn();

    }


    @Test
    public void deleteBlock() throws Exception {
        long bookingId = 3L;

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/v1/blocks/"+bookingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void deleteBlockReservationNotFound() throws Exception {
        long bookingId = 3L;
        String errorMessage = "No block found for given block id";

        doThrow(new ReservationNotFoundException(errorMessage))
                .when(reservationService)
                .deleteReservation(anyLong(), eq(ReservationType.BLOCK));

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/v1/blocks/"+bookingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(errorMessage)))
                .andReturn();

    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            objectMapper.setDateFormat(df);
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
