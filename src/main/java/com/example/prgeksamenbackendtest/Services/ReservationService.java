package com.example.prgeksamenbackendtest.Services;

import com.example.prgeksamenbackendtest.Repositories.ReservationRepository;
import com.example.prgeksamenbackendtest.Repositories.RoomRepository;
import com.example.prgeksamenbackendtest.Repositories.UserRepository;
import com.example.prgeksamenbackendtest.dto.ReservationRequestDTO;
import com.example.prgeksamenbackendtest.models.Reservation.Reservation;
import com.example.prgeksamenbackendtest.models.Room.Room;
import com.example.prgeksamenbackendtest.models.User.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ReservationRequestDTO createReservation(ReservationRequestDTO dto) {
        // Create and save the Reservation entity as before
        Reservation reservation = new Reservation();
        reservation.setReservationID(dto.getReservationID());
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());
        reservation.setRoom(roomRepository.findById(dto.getRoomID()).orElseThrow());
        reservation.setUser(userRepository.findById(dto.getUsername()).orElseThrow());
        reservation.setCreatedDate(LocalDateTime.now());
        reservation.setLastModifiedDate(LocalDateTime.now());

        // Set fields from dto...
        // ...

        reservation = reservationRepository.save(reservation);

        // Convert to ReservationDTO
        ReservationRequestDTO reservationDTO = new ReservationRequestDTO();
        reservationDTO.setReservationID(reservation.getReservationID());
        reservationDTO.setCheckInDate(reservation.getCheckInDate());
        reservationDTO.setCheckOutDate(reservation.getCheckOutDate());
        reservationDTO.setRoomID(reservation.getRoom().getRoomID());
        reservationDTO.setUsername(reservation.getUser().getUsername());

        return reservationDTO;
    }
}

