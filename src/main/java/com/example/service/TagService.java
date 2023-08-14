package com.example.service;

import com.example.dto.TagDTO;
import com.example.entity.TagEntity;
import com.example.exp.AppBadRequestException;
import com.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    public TagDTO create(TagDTO dto){
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new AppBadRequestException("Name is not Available");
        }
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        tagRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    public Boolean update(Integer id, TagDTO dto){
        TagEntity entity = get(id);
        entity.setName(dto.getName());
        int effecterRow = tagRepository.updateById(id, dto.getName());
        return effecterRow == 1;
    }
    public Boolean delete(Integer id){
        tagRepository.deleteById(id);
        return true;
    }
    public List<TagDTO> getAll(){
        Iterable<TagEntity> iterable = tagRepository.findAll();
        List<TagDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toDTO(entity)));
        return dtos;
    }

    public TagEntity get(Integer id) {
        return tagRepository.findById(id).orElseThrow(() -> new AppBadRequestException("Profilr is not founded"));
    }
    TagDTO toDTO(TagEntity entity){
        TagDTO dto = new TagDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
