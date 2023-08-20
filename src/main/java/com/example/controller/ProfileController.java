package com.example.controller;

import com.example.dto.ProfileDTO;
import com.example.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/profile")
@Tag(name = "Profile",description = "profile api list")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
//    private Logger log = (Logger) LoggerFactory.getLogger(ProfileController.class);

    @PutMapping("/change_password")
    @Operation(summary = "change password", description = "This api used for change password.")
    public ResponseEntity<Boolean> changePassword(@RequestParam("newPassword") String newPassword){
        return ResponseEntity.ok().body(profileService.changePassword(newPassword));
    }

    @PutMapping("/update_email")
    @Operation(summary = "update email", description = "This api used for update email.")
    public ResponseEntity<Boolean> updateEmail(@RequestParam("email") String email){
        return ResponseEntity.ok().body(profileService.updateEmail(email));
    }

    @PutMapping("/update/email/{jwt}")
    @Operation(summary = "update email by jwt token", description = "This api used for update email by jwt token.")
    public ResponseEntity<Boolean> updateEmailVerification(@PathVariable("jwt") String jwt){
        return ResponseEntity.ok().body(profileService.updateEmailVerification(jwt));
    }

    @PutMapping("/update_detail")
    @Operation(summary = "update detail", description = "This api used for update detail.")
    public ResponseEntity<ProfileDTO> updateDetail(@RequestBody() ProfileDTO dto){
        return ResponseEntity.ok().body(profileService.updateDetail(dto));
    }

    @PutMapping("/update_main_photo")
    @Operation(summary = "update avatar photo", description = "This api used for update avatar photo.")
    public ResponseEntity<ProfileDTO> updateMainPhoto(@RequestParam("newPhoto") String newPhoto){
        return ResponseEntity.ok().body(profileService.updateMainPhoto(newPhoto));
    }

    @GetMapping("/get_detail")
    @Operation(summary = "get detail", description = "This api used for get detail.")
    public ResponseEntity<ProfileDTO> getDetail(){
        return ResponseEntity.ok().body(profileService.getDetail());
    }

    @PostMapping("/create")
    @Operation(summary = "create profile", description = "This api used for create profile.")
    public ResponseEntity<ProfileDTO> create(ProfileDTO dto){

        return ResponseEntity.ok().body(profileService.create(dto));
    }
}
