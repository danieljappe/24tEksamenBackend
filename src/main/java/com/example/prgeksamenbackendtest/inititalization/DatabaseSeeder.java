package com.example.prgeksamenbackendtest.inititalization;

import com.example.prgeksamenbackendtest.models.Hotel.Hotel;
import com.example.prgeksamenbackendtest.Repositories.HotelRepository;
import com.example.prgeksamenbackendtest.models.Room.Room;
import com.example.prgeksamenbackendtest.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        seedHotels(250 + 1);
    }

    // Metode der laver et antal hoteller
    private void seedHotels(int numberOfHotels) {
        // For loop der laver et antal hoteller
        for (int i = 1; i < numberOfHotels; i++) {

            // Laver et hotel med et navn, adresse, by, postnummer og land
            Hotel hotel = new Hotel("Hotel " + i, "Street " + i, "City", "Zip" + i, "Country");

            // Sætter createdDate og lastModifiedDate til nu
            hotel.setCreatedDate(LocalDateTime.now());
            hotel.setLastModifiedDate(LocalDateTime.now());

            // Gemmer hotellet i databasen
            hotel = hotelRepository.save(hotel);

            // Kalder metoden seedRoomsForHotel med hotellet og et tilfældigt antal rooms (10 til 40 rooms)
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

