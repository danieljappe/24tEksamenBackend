package com.example.prgeksamenbackendtest.Repositories;

import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Page<Hotel> findByCountry(String country, Pageable pageable);
    Page<Hotel> findByCity(String city, Pageable pageable);
    Page<Hotel> findByCountryAndCity(String country, String city, Pageable pageable);


    Optional<Hotel> findByHotelID(Long hotelID);

    Hotel save(Hotel hotel);

    @Query("SELECT COUNT(r) FROM Room r WHERE r.hotel.hotelID = :hotelId")
    int countRoomsByHotelId(@Param("hotelId") Long hotelId);


}
