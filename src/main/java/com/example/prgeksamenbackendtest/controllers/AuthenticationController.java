package com.example.prgeksamenbackendtest.controllers;

import com.example.prgeksamenbackendtest.auth.AuthenticationRequest;
import com.example.prgeksamenbackendtest.auth.AuthenticationResponse;
import com.example.prgeksamenbackendtest.auth.AuthenticationService;
import com.example.prgeksamenbackendtest.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
