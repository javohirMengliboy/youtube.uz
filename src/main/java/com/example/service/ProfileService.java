package com.example.service;

import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.ProfileRepository;
import com.example.util.JWTUtil;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSenderService;

    public Boolean changePassword(String newPassword) {
        ProfileEntity entity = get();
        entity.setPassword(newPassword);
        profileRepository.save(entity);
        return true;
    }

    public Boolean updateEmail(String email) {
        ProfileEntity entity = get();
        mailSenderService.updateEmail(email, entity.getName(),entity.getId());
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
        entity.setPassword(dto.getPassword());
        entity.setRole(ProfileRole.ROLE_ADMIN);
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setPrtId(get().getId());
        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean updateEmailVerification(String jwt) {
        JwtDTO jwtDTO = JWTUtil.decodeEmailJwt(jwt);

        Optional<ProfileEntity> exists = profileRepository.findById(jwtDTO.getId());
        if (exists.isEmpty()) {
            throw new AppBadRequestException("Profile not found");
        }
        ProfileEntity entity = exists.get();
        entity.setEmail(jwtDTO.getEmail());
        profileRepository.save(entity);
        return true;
    }
}
