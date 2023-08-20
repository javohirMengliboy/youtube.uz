package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.ProfileDTO;
import com.example.enums.Language;
import com.example.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Auth api list.")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    @Operation(summary = "Registration", description = "This api used for registration.")
    public ResponseEntity<?> registration(@RequestBody ProfileDTO dto,
                                          @RequestParam(value = "lang",defaultValue = "uz") Language language){
        return ResponseEntity.ok((authService.registration(dto,language)));
    }

    @PostMapping("/authorization")
    @Operation(summary = "Authorization", description = "This api used for authorization.")
    public ResponseEntity<ApiResponseDTO> authorization(@RequestBody ProfileDTO dto){
        return ResponseEntity.ok().body(authService.authorization(dto));
    }

    @PutMapping("/verification/email/{jwt}")
    @Operation(summary = "verification by email", description = "This api used for verification by email.")
    public ResponseEntity<ApiResponseDTO> verification(@PathVariable("jwt") String jwt){
        return ResponseEntity.ok().body(authService.verification(jwt));
    }
}
