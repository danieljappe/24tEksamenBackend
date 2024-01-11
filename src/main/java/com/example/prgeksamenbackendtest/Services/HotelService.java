package com.example.prgeksamenbackendtest.Services;

import com.example.prgeksamenbackendtest.Repositories.HotelRepository;
import com.example.prgeksamenbackendtest.Repositories.RoomRepository;
import com.example.prgeksamenbackendtest.dto.HotelDTO;
import com.example.prgeksamenbackendtest.dto.RoomDTO;
import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import com.example.prgeksamenbackendtest.models.Room.Room;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomService roomService;

    public Hotel createHotel(Hotel hotel) {
        hotelRepository.save(hotel);
        return hotel;
    }

    public List<HotelDTO> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<HotelDTO> getHotelByID(Long hotelID) {
        Optional<Hotel> optionalHotel = hotelRepository.findByHotelID(hotelID);
        return optionalHotel.stream().map(this::convertToDTO).findFirst();
    }

    public HotelDTO save(HotelDTO hotelDTO) {
        Hotel hotel = convertToEntity(hotelDTO);
        hotelRepository.save(hotel);
        return convertToDTO(hotel);
    }

    public boolean deleteHotel(Long hotelID) {
        Optional<Hotel> optionalHotel = hotelRepository.findByHotelID(hotelID);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            hotelRepository.delete(hotel);
            if (hotelRepository.findByHotelID(hotelID).isEmpty()) {
                return true;
            } else {
                throw new EntityNotFoundException("Hotel not deleted");
            }
        } else {
            throw new EntityNotFoundException("Hotel not found");
        }
    }

    private Hotel convertToEntity(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        // Do not set hotelID if it's a new hotel (hotelID is null or not present in the database)
        if (hotelDTO.getHotelID() != null) {
            hotel.setHotelID(hotelDTO.getHotelID());
        }
        hotel.setHotelName(hotelDTO.getHotelName());
        hotel.setStreet(hotelDTO.getStreet());
        hotel.setCity(hotelDTO.getCity());
        hotel.setZipCode(hotelDTO.getZipCode());
        hotel.setCountry(hotelDTO.getCountry());
        hotel.setLastModifiedDate(LocalDateTime.now());
        // Find and set the Room entities
        List<Room> rooms = new ArrayList<>();
        for (RoomDTO roomDTO : hotelDTO.getRooms()) {
            Room room = roomService.convertToEntity(roomDTO);
            room.setHotel(hotel);
            rooms.add(room);
        }
        hotel.setRooms(rooms);
        return hotel;
    }


    @Transactional
    public HotelDTO updateHotel(Long hotelId, HotelDTO hotelDTO) {
        System.out.println("----------------- updateHotel -----------------");

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        // Set properties from DTO to Hotel entity
        hotel.setHotelName(hotelDTO.getHotelName());
        hotel.setStreet(hotelDTO.getStreet());
        hotel.setCity(hotelDTO.getCity());
        hotel.setZipCode(hotelDTO.getZipCode());
        hotel.setCountry(hotelDTO.getCountry());
        // Update modified date
        hotel.setLastModifiedDate(LocalDateTime.now());

        // Handling rooms
        updateHotelRooms(hotel, hotelDTO.getRooms());

        hotel.setRooms(hotel.getRooms());

        hotelRepository.save(hotel);
        System.out.println("Updated hotel: " + hotel);
        return convertToDTO(hotel);
    }

    @Autowired
    private RoomRepository roomRepository;

    private void updateHotelRooms(Hotel hotel, List<RoomDTO> roomDTOs) {
        System.out.println("----------------- updateHotelRooms -----------------");

        System.out.println("Updating hotel rooms for hotel: " + hotel.getHotelID());

        hotel.getRooms().clear(); // Clear existing rooms
        hotelRepository.save(hotel); // Save the cleared rooms (to avoid duplicate key errors
        System.out.println("Cleared rooms: " + hotel.getRooms());

        for (RoomDTO roomDTO : roomDTOs) {
            System.out.println("Processing roomDTO: " + roomDTO);
            Room room = roomService.convertToEntity(roomDTO);
            room.setHotel(hotel);
            hotel.getRooms().add(room);
            roomRepository.save(room);
        }
        hotelRepository.save(hotel);
        // Log the final state of rooms
        System.out.println("Updated rooms: " + hotel.getRooms());
    }




    // DTO

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
        dto.setHotelID(room.getHotel().getHotelID());
        return dto;
    }



    public HotelDTO createHotelDTO(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
        int numberOfRooms = hotelRepository.countRoomsByHotelId(hotelId);
        return new HotelDTO(hotel.getHotelID(), hotel.getHotelName(), hotel.getStreet(), numberOfRooms);
    }
}
