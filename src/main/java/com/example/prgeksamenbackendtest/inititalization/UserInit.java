package com.example.prgeksamenbackendtest.inititalization;

import com.example.prgeksamenbackendtest.Repositories.UserRepository;
import com.example.prgeksamenbackendtest.models.User.Role;
import com.example.prgeksamenbackendtest.models.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInit {
    @Autowired
    private UserRepository userRepository;

    public void initializeAdminAccount() {
        String adminUsername = "admin"; // Admin username
        // Check if the admin account already exists
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            // Create and save the admin user
            User adminUser = User.builder()
                    .username(adminUsername)
                    .firstName("Admin")
                    .lastName("User")
                    .email("admin@example.com")
                    .password("admin")
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(adminUser);
        }
    }
}
