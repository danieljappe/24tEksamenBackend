package com.example.prgeksamenbackendtest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.example.prgeksamenbackendtest.Services.HotelService;
import com.example.prgeksamenbackendtest.Services.RoomService;
import com.example.prgeksamenbackendtest.controllers.HotelController;
import com.example.prgeksamenbackendtest.dto.HotelDTO;
import com.example.prgeksamenbackendtest.dto.RoomDTO;
import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import com.example.prgeksamenbackendtest.models.Room.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class HotelControllerTest {

    @Mock
    private HotelService hotelService;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private HotelController hotelController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();
    }

    @Test
    public void testCreateHotel() throws Exception {
        RoomDTO room = new RoomDTO(1L, 1, 3, 400, 1L, false);
        List<RoomDTO> rooms = new ArrayList<>();
        rooms.add(room);

        HotelDTO requestHotelDTO = new HotelDTO("Hotel Name", "Hotel Street", "Hotel City", "Hotel Zipcode", "Hotel Country", rooms);

        List<Room> entityRooms = new ArrayList<>();
        entityRooms.add(roomService.convertToEntity(room));
        Hotel hotelEntity = new Hotel(1L, "Hotel Name", "Hotel Street", "Hotel City", "Hotel ZipCode", "Hotel Country", entityRooms);

        HotelDTO responseHotelDTO = new HotelDTO(1L, "Hotel Name", "Hotel Street", "Hotel City", "Hotel Zipcode", "Hotel Country", rooms.size(), rooms);

        given(hotelService.convertToEntity(requestHotelDTO)).willReturn(hotelEntity);
        given(hotelService.createHotel(hotelEntity)).willReturn(hotelEntity);
        given(hotelService.convertToDTO(hotelEntity)).willReturn(responseHotelDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        String hotelJson = objectMapper.writeValueAsString(requestHotelDTO);

        mockMvc.perform(post("/api/v1/hotel-controller/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(hotelJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.hotelName").value("Hotel Name"))
                .andExpect(jsonPath("$.street").value("Hotel Street"))
                .andExpect(jsonPath("$.city").value("Hotel City"))
                .andExpect(jsonPath("$.zipCode").value("Hotel Zipcode"))
                .andExpect(jsonPath("$.country").value("Hotel Country"));


        verify(hotelService).convertToEntity(any(HotelDTO.class));
        verify(hotelService).createHotel(any(Hotel.class));
    }

    @Test
    public void getHotelByID() throws Exception {
        // Arrange
        Long hotelId = 1L;
        HotelDTO hotelDTO = new HotelDTO(1L, "Hotel Name", "Hotel Street", 4);

        // Setting up mock behavior
        given(hotelService.getHotelByID(hotelId)).willReturn(Optional.of(hotelDTO));

        // Act & Assert
        mockMvc.perform(get("/api/v1/hotel-controller/get/{hotelId}", hotelId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.hotelID").value(hotelDTO.getHotelID()))
                .andExpect(jsonPath("$.hotelName").value(hotelDTO.getHotelName()))
                .andExpect(jsonPath("$.street").value(hotelDTO.getStreet()))
                .andExpect(jsonPath("$.numberOfRooms").value(hotelDTO.getNumberOfRooms()));
        // Verify interaction with the mock service
        verify(hotelService).getHotelByID(hotelId);
    }

    @Test
    public void testDeleteHotel() throws Exception {
        // Arrange
        Long hotelIdToDelete = 1L;
        boolean deletionResult = true; // Assuming deletion is successful

        // Setting up mock behavior
        given(hotelService.deleteHotel(hotelIdToDelete)).willReturn(deletionResult);

        // Act & Assert for Successful Deletion
        mockMvc.perform(delete("/api/v1/hotel-controller/delete/{hotelId}", hotelIdToDelete))
                .andExpect(status().isNoContent()); // Expecting 204 No Content for successful deletion

        // Verify interaction with the mock service for successful deletion
        verify(hotelService).deleteHotel(hotelIdToDelete);

        // Arrange for Unsuccessful Deletion
        Long invalidHotelId = 2L;
        given(hotelService.deleteHotel(invalidHotelId)).willReturn(false); // Assuming deletion fails

        // Act & Assert for Unsuccessful Deletion
        mockMvc.perform(delete("/api/v1/hotel-controller/delete/{hotelId}", invalidHotelId))
                .andExpect(status().isNotFound()); // Expecting 404 Not Found for unsuccessful deletion

        // Verify interaction with the mock service for unsuccessful deletion
        verify(hotelService).deleteHotel(invalidHotelId);
    }

}
