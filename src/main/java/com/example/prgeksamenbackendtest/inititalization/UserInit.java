package com.example.prgeksamenbackendtest.inititalization;

import com.example.prgeksamenbackendtest.Repositories.UserRepository;
import com.example.prgeksamenbackendtest.models.User.Role;
import com.example.prgeksamenbackendtest.models.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInit {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void initializeAdminAccount() {
        String adminUsername = "admin";
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            var adminUser = User.builder()
                    .username(adminUsername)
                    .firstName("Admin")
                    .lastName("User")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(adminUser);
        }
    }

    public void initializeUserAccount() {
        String username1 = "user1";
        if (userRepository.findByUsername(username1).isEmpty()) {
            var user1 = User.builder()
                    .username(username1)
                    .firstName("John")
                    .lastName("Doe")
                    .email("johndoe@example.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.USER)
                    .build();
            userRepository.save(user1);
        }
    }
}
