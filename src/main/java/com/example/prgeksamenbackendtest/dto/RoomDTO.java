package com.example.prgeksamenbackendtest.dto;

import lombok.*;

@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {
    private Long roomID;
    private int roomNumber;
    private int numberOfBeds;
    private int roomPrice;

    private Long hotelID;
}
