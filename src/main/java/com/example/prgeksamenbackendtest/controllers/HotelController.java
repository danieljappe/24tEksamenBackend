package com.example.prgeksamenbackendtest.controllers;

import com.example.prgeksamenbackendtest.Services.HotelService;
import com.example.prgeksamenbackendtest.dto.HotelDTO;
import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/hotel-controller")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO) {
        Hotel hotelEntity = hotelService.convertToEntity(hotelDTO);
        Hotel createdHotel = hotelService.createHotel(hotelEntity);
        HotelDTO createdHotelDTO = hotelService.convertToDTO(createdHotel);
        return new ResponseEntity<>(createdHotelDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        List<HotelDTO> hotels = hotelService.getAllHotels();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }


    @GetMapping("/get/{hotelId}")
    public ResponseEntity<HotelDTO> getHotel(@PathVariable Long hotelId) {
        Optional<HotelDTO> hotel = hotelService.getHotelByID(hotelId);
        if (hotel.isPresent()) {
            return new ResponseEntity<>(hotel.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update/{hotelId}", consumes = "application/json")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable Long hotelId, @RequestBody HotelDTO hotelDTO) {
        try {
            HotelDTO updatedHotel = hotelService.updateHotel(hotelId, hotelDTO);
            HotelDTO hotel = hotelService.save(updatedHotel);
            System.out.println("-------------------" + hotel);
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle other exceptions
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{hotelId}")
    public ResponseEntity<Boolean> deleteHotel(@PathVariable Long hotelId) {
        boolean deleted = hotelService.deleteHotel(hotelId);
        if (deleted) {
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
