package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/category")
@Tag(name = "Category", description = "category api list.")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // 1. Create Category
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "")
    @Operation(summary = "Create region", description = "This api used for region creation.")
    public ResponseEntity<CategoryDTO> add(@RequestBody CategoryDTO dto){
       return ResponseEntity.ok(categoryService.create(dto));
    }

    // 2. Update Category
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/update/{id}")
    @Operation(summary = "Update region by id", description = "This api used for update region by id.")
    public ResponseEntity<Boolean> update(@PathVariable("id")Integer id,
                                          @RequestBody CategoryDTO dto){
            return ResponseEntity.ok(categoryService.update(id,dto));
    }

    // 3. Delete Category
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Delete region", description = "This api used for region deleted.")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(categoryService.delete(id));
    }

    // 4. Category List
    @GetMapping(value = "/open/get_all")
    @Operation(summary = "Get all region", description = "This api used for get all region.")
    public ResponseEntity<List<CategoryDTO>> get(){
        return ResponseEntity.ok(categoryService.getAll());
    }

}
