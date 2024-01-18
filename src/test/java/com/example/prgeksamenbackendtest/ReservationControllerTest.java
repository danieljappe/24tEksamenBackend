package com.example.prgeksamenbackendtest;

import com.example.prgeksamenbackendtest.Services.ReservationService;
import com.example.prgeksamenbackendtest.config.JwtService;
import com.example.prgeksamenbackendtest.controllers.ReservationController;
import com.example.prgeksamenbackendtest.dto.ReservationRequestDTO;
import com.example.prgeksamenbackendtest.inititalization.UserInit;
import com.example.prgeksamenbackendtest.models.Auditable;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService; // Mock the JwtService


    @MockBean
    private ReservationService reservationService;

    @Test
    @WithMockUser
    public void getAllReservations() throws Exception{
        String username = "johndoe";
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwNTQ1MjU2MywiZXhwIjoxNzA1NDU0MDAzfQ.plBUyV1BlyWw5ekmaRkwfBJE1YuAoJqL-uLnJf629fE"; // Replace with a generated token


        ReservationRequestDTO mockReservation1 = new ReservationRequestDTO(
                1L, // reservationID
                LocalDate.of(2024, 1, 10), // checkInDate
                LocalDate.of(2024, 1, 15), // checkOutDate
                101L, // roomID
                username // username
        );

        ReservationRequestDTO mockReservation2 = new ReservationRequestDTO(
                2L, // reservationID
                LocalDate.of(2024, 2, 5), // checkInDate
                LocalDate.of(2024, 2, 10), // checkOutDate
                102L, // roomID
                username // username
        );



        List<ReservationRequestDTO> mockReservations = Arrays.asList(mockReservation1, mockReservation2);

        when(reservationService.getAllReservations(username)).thenReturn(mockReservations);

        mockMvc.perform(get("/getAllReservations/{username}", username)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        // ... other assertions
        ;

        verify(reservationService, times(1)).getAllReservations(username);
    }
}
