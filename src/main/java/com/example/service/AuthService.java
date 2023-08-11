package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private MD5Util md5Util;

    public ProfileDTO registration(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(md5Util.encode(dto.getPassword()));
        entity.setPhotoId(dto.getPhotoId());
        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean authorization(ProfileDTO dto) {
        ProfileEntity entity = profileRepository.findByEmail(dto.getEmail()).orElseThrow(()->new ItemNotFoundException("Wrong email"));
        if (!entity.getPassword().equals(md5Util.encode(dto.getPassword()))){
            throw new AppBadRequestException("Wrong password");
        }
        return true;
    }
}
