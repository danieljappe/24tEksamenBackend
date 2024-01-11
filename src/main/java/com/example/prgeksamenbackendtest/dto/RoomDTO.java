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
    // Reference back to Hotel if needed, otherwise exclude it
    // private Long hotelID;
}
