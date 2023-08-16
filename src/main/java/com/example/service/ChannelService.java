package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.ChannelDTO;
import com.example.entity.ChannelEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.ChannelMapper;
import com.example.repository.ChannelRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {
    private ChannelRepository channelRepository;
    @Autowired
    public void setChannelRepository(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    // 1. Create Channel
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

    // 2. Update Channel
    public ApiResponseDTO updateDetail(String id, ChannelDTO dto) {
        ChannelEntity entity = get(id);
        if (!SpringSecurityUtil.getCurrentUserId().equals(entity.getProfileId())){
            throw new AppBadRequestException("This channel not yours");
        }
        entity.setName(dto.getName());
        entity.setPhotoId(dto.getPhotoId());
        entity.setDescription(dto.getDescription());
        entity.setBannerId(dto.getBannerId());
        channelRepository.save(entity);
        return new ApiResponseDTO(true, "Channel changed!");

    }

    // 3. Update Channel photo
    public ApiResponseDTO updatePhoto(String id, String photoId) {
        ChannelEntity entity = get(id);
        if (!SpringSecurityUtil.getCurrentUserId().equals(entity.getProfileId())){
            throw new AppBadRequestException("This channel not yours");
        }
        entity.setPhotoId(photoId);
        channelRepository.save(entity);
        return new ApiResponseDTO(true, "Update Channel Photo");
    }

    // 4. Update Channel banner
    public ApiResponseDTO updateBanner(String id, String bannerId) {
        ChannelEntity entity = get(id);
        if (!SpringSecurityUtil.getCurrentUserId().equals(entity.getProfileId())){
            throw new AppBadRequestException("This channel not yours");
        }
        entity.setBannerId(bannerId);
        channelRepository.save(entity);
        return new ApiResponseDTO(true, "Update Channel Banner");
    }

    // 5. Channel Pagination
    public Page<ChannelMapper> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChannelMapper> channelPage = channelRepository.pagination(pageable);
        if (channelPage.isEmpty()){
            throw new ItemNotFoundException("Channel Not Found");
        }
        return channelPage;
    }
    // 6. Get Channel By Id

    public ChannelMapper getById(String id) {
        ChannelEntity entity = get(id);
        return new ChannelMapper(entity.getName(), entity.getPhotoId(), entity.getDescription(), entity.getStatus());
    }

    // 7. Change Channel Status
    public ApiResponseDTO updateStatus(String id, ProfileStatus status) {
        ChannelEntity entity = get(id);
        ProfileEntity profile = SpringSecurityUtil.getCurrentUser().getProfile();
        if (profile.getRole().equals(ProfileRole.ROLE_USER) && !profile.getId().equals(entity.getProfileId())){
            throw new AppBadRequestException("Can't change!");
        }
        entity.setStatus(status);
        channelRepository.save(entity);
        return new ApiResponseDTO(true, "Update Channel Status");
    }

    // 8. User Channel List
    public List<ChannelMapper> getChannelList() {
        List<ChannelMapper> channelMapperList = channelRepository.getChannelList(SpringSecurityUtil.getCurrentUserId());
        if (channelMapperList.isEmpty()){
            throw new ItemNotFoundException("Channel list not found");
        }
        return channelMapperList;
    }

    //--------------------------------------------
    private ChannelEntity get(String id) {
        return channelRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Channel not found"));
    }
}
