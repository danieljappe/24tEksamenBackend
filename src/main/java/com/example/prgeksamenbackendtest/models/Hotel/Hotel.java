package com.example.prgeksamenbackendtest.models.Hotel;

import com.example.prgeksamenbackendtest.models.Auditable;
import com.example.prgeksamenbackendtest.models.Room.Room;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Hotel")
public class Hotel extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelID;

    private String hotelName;
    private String street;
    private String city;
    private String zipCode;
    private String country;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms = new ArrayList<>();

    // Default Constructor
    public Hotel(String hotelName, String street, String city, String zipCode, String country) {
        this(); // Call the default constructor
        this.hotelName = hotelName;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    // Constructor for id, navn, adresse og antal værelser
    public Hotel(Long hotelID, String hotelName, String street, int numberOfRooms) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.street = street;
        this.rooms = new ArrayList<>(numberOfRooms);
    }



}
