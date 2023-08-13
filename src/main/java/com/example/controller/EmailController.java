package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.EmailDTO;
import com.example.dto.EmailFilterDTO;
import com.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    ResponseEntity<ApiResponseDTO> create(@RequestBody EmailDTO dto){
        return ResponseEntity.ok(emailService.create(dto));
    }



//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "admin/pagination")
    public ResponseEntity<PageImpl<EmailDTO>> emailPagination(@RequestParam(value = "page",defaultValue = "1")int page,
                                                              @RequestParam(value = "size",defaultValue = "10")int size){
        PageImpl<EmailDTO> pagination = emailService.emailPagination(page-1,size);
        return ResponseEntity.ok(pagination);

    }


    @PostMapping(value = "/filter")
    public ResponseEntity<PageImpl<EmailDTO>> filter(@RequestBody EmailFilterDTO filterDTO,
                                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<EmailDTO> response = emailService.filter(filterDTO,page - 1, size);
        return ResponseEntity.ok(response);
    }


}
