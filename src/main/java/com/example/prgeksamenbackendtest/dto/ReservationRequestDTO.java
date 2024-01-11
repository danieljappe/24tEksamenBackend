package com.example.prgeksamenbackendtest.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDTO {
    private Long reservationID;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long roomID;
    private String username;
}
