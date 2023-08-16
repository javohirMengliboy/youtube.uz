package com.example.controller;

import com.example.dto.ProfileDTO;
import com.example.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/change_password")
    public ResponseEntity<Boolean> changePassword(@RequestParam("newPassword") String newPassword){
        return ResponseEntity.ok().body(profileService.changePassword(newPassword));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update_email")
    public ResponseEntity<Boolean> updateEmail(@RequestParam("email") String email){
        return ResponseEntity.ok().body(profileService.updateEmail(email));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update/email/{jwt}")
    public ResponseEntity<Boolean> updateEmailVerification(@PathVariable("jwt") String jwt){
        return ResponseEntity.ok().body(profileService.updateEmailVerification(jwt));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update_detail")
    public ResponseEntity<ProfileDTO> updateDetail(@RequestBody() ProfileDTO dto){
        return ResponseEntity.ok().body(profileService.updateDetail(dto));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update_main_photo")
    public ResponseEntity<ProfileDTO> updateMainPhoto(@RequestParam("newPhoto") String newPhoto){
        return ResponseEntity.ok().body(profileService.updateMainPhoto(newPhoto));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get_detail")
    public ResponseEntity<ProfileDTO> getDetail(){
        return ResponseEntity.ok().body(profileService.getDetail());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> create(ProfileDTO dto){
        return ResponseEntity.ok().body(profileService.create(dto));
    }
}
