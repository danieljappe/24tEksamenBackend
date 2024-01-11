package com.example.prgeksamenbackendtest.Services;

import com.example.prgeksamenbackendtest.Repositories.HotelRepository;
import com.example.prgeksamenbackendtest.dto.HotelDTO;
import com.example.prgeksamenbackendtest.dto.RoomDTO;
import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import com.example.prgeksamenbackendtest.models.Room.Room;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public Hotel createHotel(Hotel hotel) {
        hotelRepository.save(hotel);
        return hotel;
    }

    public List<HotelDTO> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private HotelDTO convertToDTO(Hotel hotel) {
        HotelDTO dto = new HotelDTO();
        dto.setHotelID(hotel.getHotelID());
        dto.setHotelName(hotel.getHotelName());
        dto.setStreet(hotel.getStreet());
        dto.setCity(hotel.getCity());
        dto.setZipCode(hotel.getZipCode());
        dto.setCountry(hotel.getCountry());
        dto.setNumberOfRooms(hotel.getRooms().size());

        List<RoomDTO> roomDTOs = hotel.getRooms().stream()
                .map(this::convertRoomToDTO)
                .collect(Collectors.toList());

        dto.setRooms(roomDTOs);
        return dto;
    }

    private RoomDTO convertRoomToDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setRoomID(room.getRoomID());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setNumberOfBeds(room.getNumberOfBeds());
        dto.setRoomPrice(room.getRoomPrice());
        // dto.setHotelID(room.getHotel().getHotelID()); // if you need a back reference
        return dto;
    }

    public Optional<Hotel> getHotelByID(Long hotelID) {
        Optional<Hotel> optionalHotel = hotelRepository.findByHotelID(hotelID);
        return optionalHotel; // You can handle null value as needed
    }

    public HotelDTO createHotelDTO(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
        int numberOfRooms = hotelRepository.countRoomsByHotelId(hotelId);
        return new HotelDTO(hotel.getHotelID(), hotel.getHotelName(), hotel.getStreet(), numberOfRooms);
    }
}
