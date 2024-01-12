package com.example.prgeksamenbackendtest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.example.prgeksamenbackendtest.Services.HotelService;
import com.example.prgeksamenbackendtest.controllers.HotelController;
import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class HotelControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HotelService hotelService;

    @InjectMocks
    private HotelController hotelController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();
    }

    @Test
    public void testCreateHotel() throws Exception {
        // Opret et hotel objekt
        Hotel createdHotel = new Hotel();
        createdHotel.setHotelName("Hotel Name");
        createdHotel.setStreet("Hotel Street");
        createdHotel.setCity("Hotel City");
        createdHotel.setZipCode("Hotel Zip Code");
        createdHotel.setCountry("Hotel Country");

        // returner hotel objektet når createHotel metoden kaldes
        given(hotelService.createHotel(any(Hotel.class))).willReturn(createdHotel);

        // Opret et JSON objekt
        ObjectMapper objectMapper = new ObjectMapper();
        // Konverter hotel objektet til JSON
        String hotelJson = objectMapper.writeValueAsString(createdHotel);

        // Forvent at status er 201 (Created) og at JSON objektet er det samme som det der bliver returneret
        this.mockMvc.perform(post("/api/v1/hotel-controller/create")
                        .contentType(APPLICATION_JSON)
                        .content(hotelJson)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwNTAyODc3MSwiZXhwIjoxNzA1MDMwMjExfQ.nnzl1cG01LzVobqvOvI-1OqtzxumHoS9ywD5ZwVwjHM"))
                .andDo(MockMvcResultHandlers.print()) // This will print the response details
                .andExpect(status().isCreated())
                .andExpect(content().json(hotelJson)); // adjust this line based on what you expect in return
    }
}
