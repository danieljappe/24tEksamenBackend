package com.example.prgeksamenbackendtest.controllers;

import com.example.prgeksamenbackendtest.Services.HotelService;
import com.example.prgeksamenbackendtest.dto.HotelDTO;
import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
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
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel createdHotel = hotelService.createHotel(hotel);

        return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        List<HotelDTO> hotels = hotelService.getAllHotels();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }


    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotel(@PathVariable Long hotelId) {
        Optional<Hotel> optionalHotel = hotelService.getHotelByID(hotelId);
        if (optionalHotel.isPresent()) {
            return ResponseEntity.ok(optionalHotel.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
