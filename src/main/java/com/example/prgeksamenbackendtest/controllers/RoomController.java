package com.example.prgeksamenbackendtest.controllers;

import com.example.prgeksamenbackendtest.Repositories.HotelRepository;
import com.example.prgeksamenbackendtest.Repositories.RoomRepository;
import com.example.prgeksamenbackendtest.Services.HotelService;
import com.example.prgeksamenbackendtest.Services.RoomService;
import com.example.prgeksamenbackendtest.dto.RoomDTO;
import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import com.example.prgeksamenbackendtest.models.Room.Room;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/room-controller")
public class RoomController {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/getRoomsByHotelId/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RoomDTO>> getAllRooms(@PathVariable("hotelId") long hotelId) {
        List<RoomDTO> rooms = roomService.getAllRoomsFromID(hotelId);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PostMapping(value = "/addRoom", consumes = "application/json")
    public ResponseEntity<RoomDTO> addRoom(@RequestBody RoomDTO roomDTO, @RequestParam("hotelId") long hotelId) {
        // Find the hotel by ID
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with id: " + hotelId));

        // Convert RoomDTO to Room entity
        Room room = convertToEntity(roomDTO);

        // Set the hotel to the room
        room.setHotel(hotel);

        // Save the room
        Room savedRoom = roomRepository.save(room);

        // Convert saved room back to DTO for response
        RoomDTO savedRoomDTO = roomService.convertToDTO(savedRoom);

        return new ResponseEntity<>(savedRoomDTO, HttpStatus.CREATED);
    }

    private Room convertToEntity(RoomDTO roomDTO) {
        // Conversion logic
        Room room = new Room();
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setNumberOfBeds(roomDTO.getNumberOfBeds());
        room.setRoomPrice(roomDTO.getRoomPrice());

        return room;
    }


}
