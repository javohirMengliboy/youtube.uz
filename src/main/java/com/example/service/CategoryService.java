package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.exp.AppBadRequestException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

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
    public Boolean update(UUID id,CategoryDTO categoryDTO){
        CategoryEntity entity = get(id);
        entity.setName(categoryDTO.getName());
        int effecterRow = categoryRepository.updateById(id, categoryDTO.getName());
        return effecterRow == 1;
    }
    public Boolean delete(UUID id){
        categoryRepository.deleteById(id);
        return true;
    }
    public List<CategoryDTO> getAll(){
        Iterable<CategoryEntity> iterable = categoryRepository.findAll();
        List<CategoryDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toDTO(entity)));
        return dtos;
    }

    public CategoryEntity get(UUID profileId) {
        return categoryRepository.findById(profileId).orElseThrow(() -> new AppBadRequestException("Profilr is not founded"));
    }
    CategoryDTO toDTO(CategoryEntity entity){
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}