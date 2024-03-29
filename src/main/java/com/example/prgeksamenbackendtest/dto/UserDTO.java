package com.example.prgeksamenbackendtest.dto;

import com.example.prgeksamenbackendtest.models.Reservation.Reservation;
import com.example.prgeksamenbackendtest.models.User.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String token;


    private Role role;

    private List<ReservationRequestDTO> reservations;

    public UserDTO(String username, String firstName, String lastName, String email, String password, String token, Role role) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.token = token;
        this.role = role;
    }

}
