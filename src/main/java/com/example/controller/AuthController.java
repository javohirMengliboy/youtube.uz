package com.example.controller;

import com.example.dto.ProfileDTO;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<ProfileDTO> registration(ProfileDTO dto){
        return ResponseEntity.ok().body(authService.registration(dto));
    }

    @PostMapping("/authorization")
    public ResponseEntity<Boolean> authorization(ProfileDTO dto){
        return ResponseEntity.ok().body(authService.authorization(dto));
    }
}
