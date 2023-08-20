package com.example.controller;

import com.example.dto.AttachDTO;
import com.example.service.AttachService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Attach", description = "attach api list")
public class AttachController {

    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    @Operation(summary = "attach upload", description = "This api used for attach upload.")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(attachService.upload(file));
    }

    @GetMapping(value = "/open/{id}/img", produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(summary = "open by attach id", description = "This api used for open by attach id.")
    public byte[] openById(@PathVariable Integer id) {
        return attachService.getById(id);
    }

    @GetMapping("/download/{fileName}")
    @Operation(summary = "download by file name", description = "This api used for download by file name.")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) throws IOException {
        byte[] image = attachService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }


    @GetMapping("/admin/pagination")
    @Operation(summary = "admin pagination", description = "This api used for pagination only admin.")
    public ResponseEntity<PageImpl<AttachDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                          @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(attachService.pagination(page - 1, size));
    }


    @DeleteMapping("/admin/delete/{id}")
    @Operation(summary = "delete by id only admin", description = "This api used for delete only admin.")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok(attachService.deleteById(id));
    }

}
