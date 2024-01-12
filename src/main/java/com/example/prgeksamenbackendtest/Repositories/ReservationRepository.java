package com.example.prgeksamenbackendtest.Repositories;

import com.example.prgeksamenbackendtest.models.Reservation.Reservation;
import com.example.prgeksamenbackendtest.models.Room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.room = :room AND r.checkInDate <= :end AND r.checkOutDate >= :start")
    Optional<Reservation> findByRoomAndDateRange(@Param("room") Room room,
                                                 @Param("start") LocalDate start,
                                                 @Param("end") LocalDate end);

    List<Reservation> findByUser_Username(String username);
}
