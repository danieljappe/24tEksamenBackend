package com.example.prgeksamenbackendtest.Repositories;

import com.example.prgeksamenbackendtest.models.Room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, String> {
}
