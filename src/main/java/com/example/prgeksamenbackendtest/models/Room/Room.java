package com.example.prgeksamenbackendtest.models.Room;

import com.example.prgeksamenbackendtest.models.Auditable;
import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import com.example.prgeksamenbackendtest.models.Reservation.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Room")
public class Room extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomID;

    private int roomNumber;
    private int numberOfBeds;
    private int roomPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotelID")
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;

    public Room(int roomNumber, int numberOfBeds, int roomPrice, Hotel hotel) {
        super();
        this.roomNumber = roomNumber;
        this.numberOfBeds = numberOfBeds;
        this.roomPrice = roomPrice;
        this.hotel = hotel;
    }

}
