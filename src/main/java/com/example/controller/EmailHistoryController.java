package com.example.controller;

import com.example.dto.EmailHistoryDTO;
import com.example.dto.EmailFilterDTO;
import com.example.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryService emailService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "admin/pagination")
    public ResponseEntity<PageImpl<EmailHistoryDTO>> emailPagination(@RequestParam(value = "page",defaultValue = "1")int page,
                                                                     @RequestParam(value = "size",defaultValue = "10")int size){
        PageImpl<EmailHistoryDTO> pagination = emailService.emailPagination(page-1,size);
        return ResponseEntity.ok(pagination);

    }


    @GetMapping(value = "/pagination/by/email")
    public ResponseEntity<PageImpl<EmailHistoryDTO>> emailPaginationByEmail(@RequestParam("email")String email,@RequestParam(value = "page",defaultValue = "1")int page,
                                                                     @RequestParam(value = "size",defaultValue = "10")int size){
        PageImpl<EmailHistoryDTO> pagination = emailService.emailPaginationByEmail(email,page-1,size);
        return ResponseEntity.ok(pagination);

    }


    @PostMapping(value = "/filter")
    public ResponseEntity<PageImpl<EmailHistoryDTO>> filter(@RequestBody EmailFilterDTO filterDTO,
                                                            @RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<EmailHistoryDTO> response = emailService.filter(filterDTO,page - 1, size);
        return ResponseEntity.ok(response);
    }


}
