package com.example.controller;

import com.example.dto.EmailHistoryDTO;
import com.example.dto.EmailFilterDTO;
import com.example.service.EmailHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@Tag(name = "Email",description = "email api list")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryService emailService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "admin/pagination")
    @Operation(summary = "pagination", description = "This api used for get email by pagination.")
    public ResponseEntity<PageImpl<EmailHistoryDTO>> emailPagination(@RequestParam(value = "page",defaultValue = "1")int page,
                                                                     @RequestParam(value = "size",defaultValue = "10")int size){
        PageImpl<EmailHistoryDTO> pagination = emailService.emailPagination(page-1,size);
        return ResponseEntity.ok(pagination);

    }


    @GetMapping(value = "/pagination/by/email")
    @Operation(summary = "pagination", description = "This api used for get email pagination by email.")
    public ResponseEntity<PageImpl<EmailHistoryDTO>> emailPaginationByEmail(@RequestParam("email")String email,@RequestParam(value = "page",defaultValue = "1")int page,
                                                                     @RequestParam(value = "size",defaultValue = "10")int size){
        PageImpl<EmailHistoryDTO> pagination = emailService.emailPaginationByEmail(email,page-1,size);
        return ResponseEntity.ok(pagination);
    }


    @PostMapping(value = "/filter")
    @Operation(summary = "filter", description = "This api used for email filter.")
    public ResponseEntity<PageImpl<EmailHistoryDTO>> filter(@RequestBody EmailFilterDTO filterDTO,
                                                            @RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<EmailHistoryDTO> response = emailService.filter(filterDTO,page - 1, size);
        return ResponseEntity.ok(response);
    }


}
