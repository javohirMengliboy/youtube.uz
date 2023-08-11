package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.ProfileDTO;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponseDTO> authorization(ProfileDTO dto){
        return ResponseEntity.ok().body(authService.authorization(dto));
    }

    @PutMapping("/verification/email/{jwt}")
    public ResponseEntity<ApiResponseDTO> verification(@PathVariable("jwt") String jwt){
        return ResponseEntity.ok().body(authService.verification(jwt));
    }
}
