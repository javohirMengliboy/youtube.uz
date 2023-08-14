package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.TagDTO;
import com.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/tag")
public class TagController {
        @Autowired
    private TagService tagService;

    @PostMapping(value = "")
    public ResponseEntity<TagDTO> add(@RequestBody TagDTO dto){
        return ResponseEntity.ok(tagService.create(dto));
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody TagDTO dto){
        return ResponseEntity.ok(tagService.update(id,dto));
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(tagService.delete(id));
    }
    @GetMapping(value = "/all")
    public ResponseEntity<List<TagDTO>> get(){
        return ResponseEntity.ok(tagService.getAll());
    }
}
