package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/category")
@Tag(name = "Category", description = "category api list.")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping(value = "")
    @Operation(summary = "Create region", description = "This api used for region creation.")
    public ResponseEntity<CategoryDTO> add(@RequestBody CategoryDTO dto){
       return ResponseEntity.ok(categoryService.create(dto));
    }
    @PutMapping(value = "/update/{id}")
    @Operation(summary = "Update region by id", description = "This api used for update region by id.")

    public ResponseEntity<Boolean> update(@PathVariable("id")UUID id,
                                          @RequestBody CategoryDTO dto){
            return ResponseEntity.ok(categoryService.update(id,dto));
    }
    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Delete region", description = "This api used for region deleted.")
    public ResponseEntity<Boolean> delete(@PathVariable("id") UUID id){
        return ResponseEntity.ok(categoryService.delete(id));
    }
    @GetMapping(value = "/all")
    @Operation(summary = "Get all region", description = "This api used for get all region.")
    public ResponseEntity<List<CategoryDTO>> get(){
        return ResponseEntity.ok(categoryService.getAll());
    }

}
