package com.example.prgeksamenbackendtest.Repositories;

import com.example.prgeksamenbackendtest.models.Room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotel_HotelID(Long hotelId);
}
