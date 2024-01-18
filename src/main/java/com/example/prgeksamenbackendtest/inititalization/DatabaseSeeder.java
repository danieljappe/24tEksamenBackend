package com.example.prgeksamenbackendtest.inititalization;

import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import com.example.prgeksamenbackendtest.Repositories.HotelRepository;
import com.example.prgeksamenbackendtest.models.Room.Room;
import com.example.prgeksamenbackendtest.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Random random = new Random();

    private String[] hotelNames = {
            "Nordic Lights Hotel",
            "Royal Oak Resort",
            "Azure Bay Inn",
            "Golden Crown Suites",
            "Emerald Garden Hotel",
            "Blue Harbor Lodge",
            "Silver Pine Hotel",
            "Twilight City Hotel",
            "Majestic River Hotel",
            "Serene Valley Resort",
            // New hotel names
            "Crimson Peak Lodge",
            "Oceanfront Oasis Hotel",
            "Sunset Beach Resort",
            "Starlight Downtown Hotel",
            "Harborview Grand Inn",
            "Mountain Edge Chalet",
            "Skyline Modern Suites",
            "Royal Heritage Hotel",
            "Pinecrest Boutique Hotel",
            "Urban Retreat Hotel"
    };



    private String[] streetNames = {
            "Elm Street",
            "Cedar Road",
            "Seaside Avenue",
            "Maple Drive",
            "Highland Street",
            "Riverbank Lane",
            "Orchard Boulevard",
            "Sunrise Way",
            "Cobblestone Alley",
            "Marina Path",
            // New street names
            "Willow Park Drive",
            "Lighthouse Point Road",
            "Canyon View Lane",
            "Meadowlands Avenue",
            "Sapphire Shore Drive",
            "Hillcrest Circle",
            "Lakeside Terrace",
            "Windmill Lane",
            "Autumn Ridge Road",
            "Fountain Plaza"
    };

    private String[][] cityZipCountryPairs = {
            // Denmark
            {"Copenhagen", "1050", "Denmark"},
            {"Aarhus", "8000", "Denmark"},
            {"Odense", "5000", "Denmark"},
            {"Aalborg", "9000", "Denmark"},

            // Sweden
            {"Stockholm", "111 29", "Sweden"},
            {"Gothenburg", "41106", "Sweden"},
            {"Malmo", "21120", "Sweden"},
            {"Uppsala", "753 20", "Sweden"},

            // Germany
            {"Berlin", "10117", "Germany"},
            {"Munich", "80331", "Germany"},
            {"Frankfurt", "60311", "Germany"},
            {"Hamburg", "20095", "Germany"},

            // Italy
            {"Rome", "00184", "Italy"},
            {"Milan", "20121", "Italy"},
            {"Naples", "80133", "Italy"},
            {"Florence", "50123", "Italy"},

            // France
            {"Paris", "75001", "France"},
            {"Lyon", "69002", "France"},
            {"Marseille", "13001", "France"},
            {"Nice", "06000", "France"},

            // Spain
            {"Madrid", "28001", "Spain"},
            {"Barcelona", "08001", "Spain"},
            {"Valencia", "46001", "Spain"},
            {"Seville", "41001", "Spain"},

            // Netherlands
            {"Amsterdam", "1012", "Netherlands"},
            {"Rotterdam", "3011", "Netherlands"},
            {"The Hague", "2511", "Netherlands"},
            {"Eindhoven", "5600", "Netherlands"}
    };


    @Override
    public void run(String... args) throws Exception {
        seedHotels(150 + 1);
    }

    // Metode der laver et antal hoteller
    private void seedHotels(int numberOfHotels) {
        for (int i = 1; i < numberOfHotels; i++) {
            // Select random elements from arrays
            String hotelName = hotelNames[random.nextInt(hotelNames.length)];
            String streetName = streetNames[random.nextInt(streetNames.length)];

            // Selecting a random city-zip-country pair
            int pairIndex = random.nextInt(cityZipCountryPairs.length);
            String[] selectedPair = cityZipCountryPairs[pairIndex];
            String cityName = selectedPair[0];
            String zipCode = selectedPair[1];
            String countryName = selectedPair[2];

            // Create a new hotel
            Hotel hotel = new Hotel(hotelName, streetName, cityName, zipCode, countryName);

            // Set created and last modified dates
            hotel.setCreatedDate(LocalDateTime.now());
            hotel.setLastModifiedDate(LocalDateTime.now());

            // Save hotel to the database
            hotel = hotelRepository.save(hotel);

            // Seed rooms for this hotel
            seedRoomsForHotel(hotel, 10 + random.nextInt(31));
        }
    }


    // Metode der laver et antal rooms for et hotel
    private void seedRoomsForHotel(Hotel hotel, int numberOfRooms) {

        // For loop der laver et antal rooms
        for (int i = 0; i < numberOfRooms; i++) {
            // Genererer roomNumber, numberOfBeds og roomPrice
            int roomNumber = hotel.getRooms().size() + 1;
            int numberOfBeds = 1 + random.nextInt(4); // Random mellem 1 and 4
            int roomPrice = 300 + random.nextInt(201); // Random mellem 300 and 500

            // Instantierer et room med generede roomNumber, numberOfBeds, roomPrice og et tilsvarende hotel
            Room room = new Room(roomNumber, numberOfBeds, roomPrice, hotel);

            // Sætter createdDate og lastModifiedDate til nu
            room.setCreatedDate(LocalDateTime.now());
            room.setLastModifiedDate(LocalDateTime.now());

            // Gemmer room i hotellet og i databasen
            room.setHotel(hotel);
            roomRepository.save(room);

            // Tilføjer room til hotellet
            hotel.getRooms().add(room);
        }

        // Gemmer hotellet i databasen
        hotelRepository.save(hotel);
    }

}

