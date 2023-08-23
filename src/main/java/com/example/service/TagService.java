package com.example.service;

import com.example.dto.TagDTO;
import com.example.entity.TagEntity;
import com.example.exp.AppBadRequestException;
import com.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    // 1. Create Tag
    public TagDTO create(TagDTO dto){
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new AppBadRequestException("Name is not Available");
        }
        Optional<TagEntity> optional = tagRepository.findByName(dto.getName());
        if (optional.isPresent()){
            throw new AppBadRequestException("This tag already created");
        }
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        tagRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    // 2. Update Tag
    public Boolean update(UUID id, TagDTO dto){
        TagEntity entity = get(id);
        entity.setName(dto.getName());
        tagRepository.save(entity);
        return true;
    }

    // 3. Delete Tag
    public Boolean delete(UUID id){
        TagEntity entity = get(id);
        tagRepository.delete(entity);
        return true;
    }

    // 4. Tag List
    public List<TagDTO> getAll(){
        Iterable<TagEntity> iterable = tagRepository.findAll();
        List<TagDTO> dtoList = new LinkedList<>();
        iterable.forEach(entity -> dtoList.add(toDTO(entity)));
        return dtoList;
    }

    //---------------------------------------------------------
    public TagEntity get(Integer id) {
        return tagRepository.findById(id).orElseThrow(() -> new AppBadRequestException("Tag not found"));
    }
    TagDTO toDTO(TagEntity entity){
        TagDTO dto = new TagDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
