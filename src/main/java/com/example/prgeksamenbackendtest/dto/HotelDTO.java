package com.example.prgeksamenbackendtest.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelDTO {

    private Long hotelID;
    private String hotelName;
    private String street;
    private String city;
    private String zipCode;
    private String country;

    private int numberOfRooms;
    private List<RoomDTO> rooms;

    // Default constructor with rooms
    public HotelDTO(String hotelName, String street, String city, String zipCode, String country, List<RoomDTO> rooms) {
        this.hotelName = hotelName;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.numberOfRooms = rooms.size();
        this.rooms = rooms;
    }

    // Constructor for id, navn, adresse og antal værelser
    public HotelDTO(Long hotelID, String hotelName, String street, int numberOfRooms) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.street = street;
        this.numberOfRooms = numberOfRooms;
    }
}
