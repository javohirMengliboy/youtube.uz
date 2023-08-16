package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.TagDTO;
import com.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/tag")
public class TagController {
        @Autowired
    private TagService tagService;

    // 1. Create Tag
    @PostMapping(value = "/open/create")
    public ResponseEntity<TagDTO> add(@RequestBody TagDTO dto){
        return ResponseEntity.ok(tagService.create(dto));
    }

    // 2. Update Tag
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody TagDTO dto){
        return ResponseEntity.ok(tagService.update(id,dto));
    }

    // 3. Delete Tag
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(tagService.delete(id));
    }

    // 4. Tag List
    @GetMapping(value = "/open/get_all")
    public ResponseEntity<List<TagDTO>> get(){
        return ResponseEntity.ok(tagService.getAll());
    }
}
