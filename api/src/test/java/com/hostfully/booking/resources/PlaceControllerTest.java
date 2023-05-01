package com.hostfully.booking.resources;

import com.hostfully.booking.services.PlaceService;
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PlaceController.class)
public class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceService placeService;


    @Test
    public void getUnavailableDates() throws Exception {

        Long placeId = 1L;

        when(placeService.getDatesTaken(anyLong(), eq(Optional.empty())))
                .thenReturn(Collections.singletonList(LocalDate.now()));


        RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/places/"+placeId+"/unavailableDates")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(LocalDate.now().toString())))
                .andReturn();

    }

    @Test
    public void getUnavailableDatesAllFree() throws Exception {

        Long placeId = 1L;

        when(placeService.getDatesTaken(anyLong(), eq(Optional.empty())))
                .thenReturn(Collections.emptyList());


        RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/places/"+placeId+"/unavailableDates")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andReturn();

    }

    @Test
    public void getUnavailableDatesPerBooking() throws Exception {

        long placeId = 1L;
        Long bookingId = 1L;


        when(placeService.getDatesTaken(anyLong(), eq(Optional.of(bookingId))))
                .thenReturn(Collections.singletonList(LocalDate.now()));


        RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/places/"+placeId+"/unavailableDates/"+bookingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(LocalDate.now().toString())))
                .andReturn();

    }

    @Test
    public void getUnavailableDatesPerBookingAllFree() throws Exception {

        long placeId = 1L;
        Long bookingId = 1L;


        when(placeService.getDatesTaken(anyLong(), eq(Optional.of(bookingId))))
                .thenReturn(Collections.emptyList());


        RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/places/"+placeId+"/unavailableDates/"+bookingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andReturn();

    }
}
