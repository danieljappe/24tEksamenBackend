package com.example.prgeksamenbackendtest.controllers;

import com.example.prgeksamenbackendtest.Services.UserService;
import com.example.prgeksamenbackendtest.auth.AuthenticationService;
import com.example.prgeksamenbackendtest.dto.UserDTO;
import com.example.prgeksamenbackendtest.models.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        UserDTO userDTO = userService.getUserByUsername(username);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


}
