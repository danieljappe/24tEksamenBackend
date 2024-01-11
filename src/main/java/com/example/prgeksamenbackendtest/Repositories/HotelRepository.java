package com.example.prgeksamenbackendtest.Repositories;

import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByHotelID(Long hotelID);

    Hotel save(Hotel hotel);
}
