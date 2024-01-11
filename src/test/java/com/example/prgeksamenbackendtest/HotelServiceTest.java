package com.example.prgeksamenbackendtest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.prgeksamenbackendtest.Repositories.HotelRepository;
import com.example.prgeksamenbackendtest.Services.HotelService;
import com.example.prgeksamenbackendtest.dto.HotelDTO;
import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateHotelDTO() {
        Long hotelId = 1L;
        Hotel mockHotel = new Hotel(hotelId, "Test Hotel", "Test Street", "Test City", "12345", "Test Country");
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(mockHotel));
        when(hotelRepository.countRoomsByHotelId(hotelId)).thenReturn(10);

        HotelDTO hotelDTO = hotelService.createHotelDTO(hotelId);

        assertNotNull(hotelDTO);
        assertEquals(hotelId, hotelDTO.getHotelID());
        assertEquals("Test Hotel", hotelDTO.getHotelName());
        assertEquals(10, hotelDTO.getNumberOfRooms());
        // Add more assertions as necessary
    }
}
