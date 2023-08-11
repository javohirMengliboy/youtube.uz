package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.exp.ItemNotFoundException;
import com.example.repository.ProfileRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Boolean changePassword(String newPassword) {
        ProfileEntity entity = get();
        entity.setPassword(newPassword);
        profileRepository.save(entity);
        return true;
    }

    public Boolean updateEmail(String email) {
        ProfileEntity entity = get();
        entity.setPassword(email);
        profileRepository.save(entity);
        return true;
    }

    public ProfileDTO updateDetail(ProfileDTO dto) {
        ProfileEntity entity = get();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPhotoId(entity.getPhotoId());
        return dto;
    }

    public ProfileDTO updateMainPhoto(String newPhoto) {
        ProfileEntity entity = get();
        entity.setPhotoId(newPhoto);
        profileRepository.save(entity);
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ProfileEntity get(){
        return profileRepository.findById(SpringSecurityUtil.getCurrentUserId()).orElseThrow(()-> new ItemNotFoundException("Profile not found"));
    }

    public ProfileDTO getDetail() {
        ProfileEntity entity = get();
        return new ProfileDTO(entity.getId(), entity.getName(), entity.getSurname(), entity.getEmail(), entity.getPhotoId());
    }

    public ProfileDTO create(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
