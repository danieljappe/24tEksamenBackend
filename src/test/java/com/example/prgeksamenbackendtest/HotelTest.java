package com.example.prgeksamenbackendtest;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import com.example.prgeksamenbackendtest.Repositories.HotelRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    public void createHotel() {
        Hotel hotel = new Hotel("The Grand Hotel", "123 Main Street", "New York", "10001", "USA");
        hotel.setCreatedDate(LocalDateTime.now());
        hotel.setLastModifiedDate(LocalDateTime.now());
        hotelRepository.save(hotel);

        List<Hotel> hotels = hotelRepository.findAll();
        assertEquals(1, hotels.size());
        assertEquals("The Grand Hotel", hotels.get(0).getHotelName());
    }

    @Test
    public void findHotelById() {
        Hotel hotel = new Hotel("The Ritz-Carlton", "456 Park Avenue", "Boston", "02121", "USA");
        hotel.setCreatedDate(LocalDateTime.now());
        hotel.setLastModifiedDate(LocalDateTime.now());
        hotelRepository.save(hotel);

        Hotel foundHotel = hotelRepository.findById(hotel.getHotelID()).get();
        assertEquals("The Ritz-Carlton", foundHotel.getHotelName());
    }

    @Test
    public void updateHotel() {
        Hotel hotel = new Hotel("The Marriott", "789 Broadway", "San Francisco", "94111", "USA");
        hotel.setCreatedDate(LocalDateTime.now());
        hotel.setLastModifiedDate(LocalDateTime.now());
        hotelRepository.save(hotel);

        hotel.setHotelName("The W Hotel");
        hotel.setLastModifiedDate(LocalDateTime.now());
        hotelRepository.save(hotel);

        Hotel updatedHotel = hotelRepository.findById(hotel.getHotelID()).get();
        assertEquals("The W Hotel", updatedHotel.getHotelName());
    }

    @Test
    public void deleteHotel() {
        Hotel hotel = new Hotel("The Four Seasons", "1010 Peachtree Street", "Atlanta", "30309", "USA");
        hotel.setCreatedDate(LocalDateTime.now());
        hotel.setLastModifiedDate(LocalDateTime.now());

        hotelRepository.save(hotel);
        hotelRepository.delete(hotel);

        List<Hotel> hotels = hotelRepository.findAll();
        assertTrue(hotels.isEmpty());
    }
}
