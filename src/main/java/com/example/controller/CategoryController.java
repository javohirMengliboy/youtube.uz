package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping(value = "")
    public ResponseEntity<CategoryDTO> add(@RequestBody CategoryDTO dto){
       return ResponseEntity.ok(categoryService.create(dto));
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id")Integer id,
                                          @RequestBody CategoryDTO dto){
            return ResponseEntity.ok(categoryService.update(id,dto));
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(categoryService.delete(id));
    }
    @GetMapping(value = "/all")
    public ResponseEntity<List<CategoryDTO>> get(){
        return ResponseEntity.ok(categoryService.getAll());
    }

}
