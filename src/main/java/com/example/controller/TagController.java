package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.TagDTO;
import com.example.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/tag")
@Tag(name = "Tag",description = "tag api list")
public class TagController {
        @Autowired
    private TagService tagService;

    @PostMapping(value = "")
    @Operation(summary = "add tag", description = "This api used for add tag.")
    public ResponseEntity<TagDTO> add(@RequestBody TagDTO dto){
        return ResponseEntity.ok(tagService.create(dto));
    }
    @PutMapping(value = "/update/{id}")
    @Operation(summary = "update tag", description = "This api used for update by id.")
    public ResponseEntity<Boolean> update(@PathVariable("id") UUID id,
                                          @RequestBody TagDTO dto){
        return ResponseEntity.ok(tagService.update(id,dto));
    }
    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "delete tag", description = "This api used for delete by id.")
    public ResponseEntity<Boolean> delete(@PathVariable("id") UUID id){
        return ResponseEntity.ok(tagService.delete(id));
    }
    @GetMapping(value = "/all")
    @Operation(summary = "get all tag", description = "This api used for get all tag.")
    public ResponseEntity<List<TagDTO>> get(){
        return ResponseEntity.ok(tagService.getAll());
    }
}
