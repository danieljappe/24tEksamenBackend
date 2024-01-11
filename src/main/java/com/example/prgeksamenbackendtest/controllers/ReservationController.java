package com.example.prgeksamenbackendtest.controllers;

import com.example.prgeksamenbackendtest.Services.ReservationService;
import com.example.prgeksamenbackendtest.dto.ReservationRequestDTO;
import com.example.prgeksamenbackendtest.models.Reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping(value = "/makeReservation", consumes = "application/json")
    public ResponseEntity<ReservationRequestDTO> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        ReservationRequestDTO reservationDTO = reservationService.createReservation(reservationRequestDTO);
        return new ResponseEntity<>(reservationDTO, HttpStatus.CREATED);
    }
}
