package com.example.prgeksamenbackendtest.Repositories;

import com.example.prgeksamenbackendtest.models.Reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
}
