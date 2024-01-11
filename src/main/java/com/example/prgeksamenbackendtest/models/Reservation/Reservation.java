package com.example.prgeksamenbackendtest.models.Reservation;

import com.example.prgeksamenbackendtest.models.Auditable;
import com.example.prgeksamenbackendtest.models.Room.Room;
import com.example.prgeksamenbackendtest.models.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@ToString(exclude = {"room", "user"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Reservation")
public class Reservation extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationID;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomID")
    @JsonBackReference
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    @JsonManagedReference
    private User user;

}
