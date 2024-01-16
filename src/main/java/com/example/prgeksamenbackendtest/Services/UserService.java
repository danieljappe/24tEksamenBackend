package com.example.prgeksamenbackendtest.Services;

import com.example.prgeksamenbackendtest.Repositories.UserRepository;
import com.example.prgeksamenbackendtest.dto.ReservationRequestDTO;
import com.example.prgeksamenbackendtest.dto.UserDTO;
import com.example.prgeksamenbackendtest.models.Reservation.Reservation;
import com.example.prgeksamenbackendtest.models.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationService reservationService;


    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return null;

        return convertToUserDTO(user);
    }

    public UserDTO convertToUserDTO(User user) {
        List<ReservationRequestDTO> reservationDTOs = user.getReservations().stream()
                .map(this::convertToReservationDTO)
                .collect(Collectors.toList());

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());


        userDTO.setReservations(reservationDTOs);
        return userDTO;
    }

    private ReservationRequestDTO convertToReservationDTO(Reservation reservation) {
        // Create a new ReservationRequestDTO object and set its properties from the Reservation entity
        ReservationRequestDTO reservationDTO = reservationService.convertToDTO(reservation);
        return reservationDTO;
    }


}
