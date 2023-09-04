package com.example.controller;

import com.example.dto.AttachDTO;
import com.example.service.AttachService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/attach")
public class AttachController {

    @Autowired
    private AttachService attachService;

    // 1. Attach upload
    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(attachService.upload(file));
    }

    // 2. Open by id
    @GetMapping(value = "/open/{id}/img", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] openById(@PathVariable String id){
        return attachService.getById(id);
    }

    // 2. Open General
    @GetMapping(value = "/open/{id}/general")
    public ResponseEntity<byte[]> openByIdGeneral(@PathVariable("id") String id) {
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("video/mp4")).body(attachService.loadByIdGeneral(id));
    }

    // 3. Download Attach
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) throws IOException {
        byte[] image = attachService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }

    // 4. Attach pagination
    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<AttachDTO>>pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                         @RequestParam(value = "size", defaultValue ="10") Integer size){
        return ResponseEntity.ok(attachService.pagination(page,size));
    }

    // 5. Delete Attach
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean>deleteById(@PathVariable String id){
        return ResponseEntity.ok(attachService.deleteById(id));
    }

}
