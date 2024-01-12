package com.example.prgeksamenbackendtest.controllers;

import com.example.prgeksamenbackendtest.Services.ReservationService;
import com.example.prgeksamenbackendtest.dto.ReservationRequestDTO;
import com.example.prgeksamenbackendtest.models.Reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getAllReservations/{username}")
    public ResponseEntity<List<ReservationRequestDTO>> getAllReservations(@PathVariable("username") String username) {
        List<ReservationRequestDTO> reservations = reservationService.getAllReservations(username);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @DeleteMapping("/deleteReservation/{reservationId}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable("reservationId") long reservationId) {
        Reservation reservation = reservationService.deleteReservation(reservationId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }
}
