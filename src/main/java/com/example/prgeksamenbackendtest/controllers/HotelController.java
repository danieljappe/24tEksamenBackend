package com.example.prgeksamenbackendtest.controllers;

import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/hotel-controller")
public class HotelController {


    @PostMapping("/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel createdHotel =

        return null;
    }
}
