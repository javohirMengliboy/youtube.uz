package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.ChannelDTO;
import com.example.entity.ChannelEntity;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.ChannelMapper;
import com.example.repository.ChannelRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Optional;

@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;

    public ChannelDTO create(ChannelDTO dto) {
        // TODO Name check
        Optional<ChannelEntity> optional = channelRepository.findByName(dto.getName());
        if (optional.isPresent()){
            throw new AppBadRequestException("This name already used");
        }

        // TODO Description check

        ChannelEntity entity = new ChannelEntity();
        entity.setName(dto.getName());
        entity.setPhotoId(dto.getPhotoId());
        entity.setDescription(dto.getDescription());
        entity.setBannerId(dto.getBannerId());
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setProfileId(SpringSecurityUtil.getCurrentUserId());
        channelRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setStatus(entity.getStatus());
        dto.setProfileId(entity.getProfileId());
        return dto;
    }

    public ApiResponseDTO updateDetail(String id, ChannelDTO dto) {
        ChannelEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setPhotoId(dto.getPhotoId());
        entity.setDescription(dto.getDescription());
        entity.setBannerId(dto.getBannerId());
        channelRepository.save(entity);
        return new ApiResponseDTO(true, "Channel changed!");

    }

    public Boolean updatePhoto(String id, String photoId) {
        ChannelEntity entity = get(id);
        entity.setPhotoId(photoId);
        channelRepository.save(entity);
        return true;
    }

    private ChannelEntity get(String id) {
        return channelRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Channel not found"));
    }

    public Boolean updateBanner(String id, String bannerId) {
        ChannelEntity entity = get(id);
        entity.setBannerId(bannerId);
        channelRepository.save(entity);
        return true;
    }

    public Boolean updateStatus(String id, ProfileStatus status) {
        ChannelEntity entity = get(id);
        entity.setStatus(status);
        channelRepository.save(entity);
        return true;
    }


    public ChannelMapper pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
    }
}
