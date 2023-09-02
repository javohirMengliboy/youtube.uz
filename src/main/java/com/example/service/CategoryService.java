package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.exp.AppBadRequestException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // 1. Create Category
    public CategoryDTO create(CategoryDTO dto){
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new AppBadRequestException("Name is not Available");
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    // 2. Update Category
    @CachePut(value = "category", key = "#id")
    public CategoryEntity update(Integer id,CategoryDTO categoryDTO){
        CategoryEntity entity = get(id);
        entity.setName(categoryDTO.getName());
        int effecterRow = categoryRepository.updateById(id, categoryDTO.getName());
        return entity;
    }

    // 3. Delete Category
    public Boolean delete(Integer id){
        categoryRepository.deleteById(id);
        return true;
    }

    // 4. Category List
    @Cacheable(value = "categoryList")
    public List<CategoryDTO> getAll(){
        Iterable<CategoryEntity> iterable = categoryRepository.findAll();
        List<CategoryDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toDTO(entity)));
        return dtos;
    }

    //---------------------------------------------------------------
    @Cacheable(value = "category", key = "#categoryId")
    public CategoryEntity get(Integer categoryId) {
        System.out.println("GET");
        return categoryRepository.findById(categoryId).orElseThrow(() -> new AppBadRequestException("Profilr is not founded"));
    }

    CategoryDTO toDTO(CategoryEntity entity){
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
