package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.VideoDTO;
import com.example.dto.VideoShortInfoDTO;
import com.example.entity.VideoEntity;
import com.example.enums.PlaylistStatus;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.VideoShortInfo;
import com.example.repository.VideoRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;

    // 1. Create Video
    public VideoDTO create(VideoDTO dto) {
        VideoEntity entity = new VideoEntity();
        entity.setId(dto.getId());
        entity.setAttachId(dto.getAttachId());
        entity.setPreviewAttachId(dto.getPreviewAttachId());
        entity.setTitle(dto.getTitle());
        entity.setCategoryId(dto.getCategoryId());
        entity.setStatus(dto.getStatus());
        entity.setType(dto.getType());
        entity.setDescription(dto.getDescription());
        entity.setChannelId(dto.getChannelId());
        entity.setProfileId(SpringSecurityUtil.getCurrentUserId());
        entity.setViewCount(dto.getViewCount());
        entity.setSharedCount(dto.getSharedCount());
        entity.setLikeCount(dto.getLikeCount());
        entity.setDislikeCount(dto.getDislikeCount());
        entity.setPublishedDate(LocalDateTime.now().plusHours(18));
        videoRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setProfileId(entity.getProfileId());
        dto.setPublishedDate(entity.getPublishedDate());
        return dto;
    }

    // 2. Update Video Detail
    public ApiResponseDTO update(String id, VideoDTO dto) {
        VideoEntity entity = get(id);
        if (!SpringSecurityUtil.getCurrentUserId().equals(getOwnerId(entity.getId()))){
            throw new AppBadRequestException("This video not yours");
        }
        entity.setAttachId(dto.getAttachId());
        entity.setPreviewAttachId(dto.getPreviewAttachId());
        entity.setTitle(dto.getTitle());
        entity.setCategoryId(dto.getCategoryId());
        entity.setStatus(dto.getStatus());
        entity.setType(dto.getType());
        entity.setDescription(dto.getDescription());
        entity.setChannelId(dto.getChannelId());
        entity.setProfileId(SpringSecurityUtil.getCurrentUserId());
        videoRepository.save(entity);
        return new ApiResponseDTO(true, "Video successfully update");

    }



    // 3. Change Video Status
    public ApiResponseDTO updateStatus(String id, PlaylistStatus status) {
        VideoEntity entity = get(id);
        if (!SpringSecurityUtil.getCurrentUserId().equals(getOwnerId(entity.getId()))){
            throw new AppBadRequestException("This video not yours");
        }
        entity.setStatus(status);
        videoRepository.save(entity);
        return new ApiResponseDTO(true, "Video status successfully update");
    }

    // 4. Increase video_view Count
    public VideoShortInfo increaseViewCount(String id) {
        VideoEntity entity = new VideoEntity();
        entity.setId(id);
        entity.setViewCount(entity.getViewCount()+1);
        videoRepository.save(entity);
        return new VideoShortInfo(entity.getTitle(), entity.getViewCount());
    }

    // 5. Get Video Pagination by CategoryId
    public Page<VideoShortInfo> getPageByCategory(int categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoShortInfo> videoShortInfoPage = videoRepository.getPageByCategory(categoryId,pageable);
        if (videoShortInfoPage.isEmpty()){
            throw new ItemNotFoundException("Video page not found");
        }
        return videoShortInfoPage;
    }

    // 6. Search video by Title
    public List<VideoShortInfo> searchByTitle(String title) {
        title = title.toLowerCase();
        List<VideoShortInfo> videoShortInfoList = videoRepository.searchByTitle("%"+title+"%");
        if (videoShortInfoList.isEmpty()){
            throw new ItemNotFoundException("Video list not found");
        }
        return videoShortInfoList;
    }

    // 7. Get video by tag_id with pagination
    public Page<VideoShortInfo> getPageByTag(int tagId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoShortInfo> videoShortInfoPage = videoRepository.getPageByTag(tagId,pageable);
        if (videoShortInfoPage.isEmpty()){
            throw new ItemNotFoundException("Video page not found");
        }
        return videoShortInfoPage;
    }

    private VideoEntity get(String id) {
        return videoRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Video not found"));
    }
    public String getOwnerId(String videoId) {
        return videoRepository.getOwnerId(videoId).orElseThrow(()->new ItemNotFoundException("Video not found"));
    }
}
