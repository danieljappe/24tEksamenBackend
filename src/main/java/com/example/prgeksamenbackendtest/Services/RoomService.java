package com.example.prgeksamenbackendtest.Services;

import com.example.prgeksamenbackendtest.Repositories.HotelRepository;
import com.example.prgeksamenbackendtest.Repositories.RoomRepository;
import com.example.prgeksamenbackendtest.dto.HotelDTO;
import com.example.prgeksamenbackendtest.dto.RoomDTO;
import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import com.example.prgeksamenbackendtest.models.Room.Room;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room convertToEntity(RoomDTO roomDTO) {
        Room room = new Room();
        // Do not set roomID if it's a new room (roomID is null or not present in the database)
        if (roomDTO.getRoomID() != null) {
            room.setRoomID(roomDTO.getRoomID());
        }
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setNumberOfBeds(roomDTO.getNumberOfBeds());
        room.setRoomPrice(roomDTO.getRoomPrice());
        room.setLastModifiedDate(LocalDateTime.now());
        // Find and set the Hotel entity
        Hotel hotel = hotelRepository.findById(roomDTO.getHotelID())
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
        room.setHotel(hotel);
        return room;
    }

    public void updateRoomEntityFromDTO(Room room, RoomDTO roomDTO) {
        // Assuming roomID and hotelID do not change or are handled elsewhere
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setNumberOfBeds(roomDTO.getNumberOfBeds());
        room.setRoomPrice(roomDTO.getRoomPrice());
        // Do not set hotel here as it's already associated and might not change
    }


}
