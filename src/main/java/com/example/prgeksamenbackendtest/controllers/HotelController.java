package com.example.prgeksamenbackendtest.controllers;

import com.example.prgeksamenbackendtest.Services.HotelService;
import com.example.prgeksamenbackendtest.dto.HotelDTO;
import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO) {
        Hotel hotelEntity = hotelService.convertToEntity(hotelDTO);
        Hotel createdHotel = hotelService.createHotel(hotelEntity);
        HotelDTO createdHotelDTO = hotelService.convertToDTO(createdHotel);
        return new ResponseEntity<>(createdHotelDTO, HttpStatus.CREATED);
    }

    @GetMapping("/get/all")
    public ResponseEntity<Page<HotelDTO>> getAllHotels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city) {

        Pageable pageable = PageRequest.of(page, size);
        Page<HotelDTO> hotelPage = hotelService.getAllHotels(pageable, country, city);
        return new ResponseEntity<>(hotelPage, HttpStatus.OK);
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
