package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadRequestException;
import com.example.repository.ProfileRepository;
import com.example.util.JWTUtil;
import com.example.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private MD5Util md5Util;

    @Autowired
    private ResourceBundleService resourceBundleService;

    public Object registration(ProfileDTO dto, Language language) {

        Optional<ProfileEntity> exists = profileRepository.findByEmail(dto.getEmail());
        if (exists.isPresent()) {
            if (exists.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(exists.get());
            } else {
                return new ApiResponseDTO(false, resourceBundleService.getMessage("email.already.exists", language));
            }
        }


        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(md5Util.encode(dto.getPassword()));
        entity.setPhotoId(dto.getPhotoId());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);
        profileRepository.save(entity);
        mailSenderService.sendEmailVerification(dto.getEmail(), entity.getName(), entity.getId(), entity.getEmail());
        dto.setId(entity.getId());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ApiResponseDTO authorization(ProfileDTO dto) {
        System.out.println(dto.getEmail());
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        System.out.println(optional.get().getEmail());
        if (optional.isEmpty()) {
            return new ApiResponseDTO(false, "Email wrong");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getPassword().equals(md5Util.encode(dto.getPassword()))) {
            return new ApiResponseDTO(false, "Password wrong");
        }
        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            return new ApiResponseDTO(false, "Your status not active");
        }

        ProfileDTO response = new ProfileDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setSurname(entity.getSurname());
        response.setRole(entity.getRole());
        response.setEmail(entity.getEmail());
        response.setJwt(JWTUtil.encode(entity.getId(), entity.getEmail()));
        return new ApiResponseDTO(true, response);
    }

    public ApiResponseDTO verification(String jwt) {
        JwtDTO jwtDTO = JWTUtil.decodeEmailJwt(jwt);

        Optional<ProfileEntity> exists = profileRepository.findByEmail(jwtDTO.getEmail());
        if (exists.isEmpty()) {
            throw new AppBadRequestException("Profile not found");
        }

        ProfileEntity entity = exists.get();
        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadRequestException("Wrong status");
        }

        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity); // update
        return new ApiResponseDTO(true, "Registration completed");
    }
}
