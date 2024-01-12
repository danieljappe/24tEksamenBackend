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
import java.util.List;
import java.util.Optional;

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
        Room room = roomRepository.findById(dto.getRoomID())
                .orElseThrow(() -> new RuntimeException("Room not found")); // Handle this properly

        // Check for existing reservation
        Optional<Reservation> existingReservation = reservationRepository
                .findByRoomAndDateRange(room, dto.getCheckInDate(), dto.getCheckOutDate());
        if (existingReservation.isPresent()) {
            // Handle this scenario appropriately, e.g., throw a custom exception
            throw new RuntimeException("Room is already booked for the selected dates");
        }

        // Proceed to create the new reservation as no conflicting reservation exists
        Reservation reservation = new Reservation();
        reservation.setReservationID(dto.getReservationID());
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());
        reservation.setLastModifiedDate(LocalDateTime.now());
        reservation.setRoom(room);
        reservation.setUser(userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"))); // Handle this properly

        reservation = reservationRepository.save(reservation);

        // Convert to ReservationRequestDTO
        ReservationRequestDTO reservationDTO = convertToReservationRequestDTO(reservation);
        return reservationDTO;
    }

    private ReservationRequestDTO convertToReservationRequestDTO(Reservation reservation) {
        ReservationRequestDTO reservationDTO = new ReservationRequestDTO();
        reservationDTO.setReservationID(reservation.getReservationID());
        reservationDTO.setCheckInDate(reservation.getCheckInDate());
        reservationDTO.setCheckOutDate(reservation.getCheckOutDate());
        reservationDTO.setRoomID(reservation.getRoom().getRoomID());
        reservationDTO.setUsername(reservation.getUser().getUsername());
        return reservationDTO;
    }


    public List<ReservationRequestDTO> getAllReservations(String username) {
        List<Reservation> reservations = reservationRepository.findByUser_Username(username);
        return reservations.stream().map(this::convertToDTO).toList();
    }

    public ReservationRequestDTO convertToDTO(Reservation reservation) {
        ReservationRequestDTO reservationDTO = new ReservationRequestDTO();
        reservationDTO.setReservationID(reservation.getReservationID());
        reservationDTO.setCheckInDate(reservation.getCheckInDate());
        reservationDTO.setCheckOutDate(reservation.getCheckOutDate());
        reservationDTO.setRoomID(reservation.getRoom().getRoomID());
        reservationDTO.setUsername(reservation.getUser().getUsername());
        return reservationDTO;
    }

    public Reservation deleteReservation(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        reservationRepository.delete(reservation);
        return reservation;
    }
}

