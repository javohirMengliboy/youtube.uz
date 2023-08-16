package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.TagDTO;
import com.example.dto.VideoAndTagDTO;
import com.example.entity.VideoAndTagEntity;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.VideoAndTagMapper;
import com.example.repository.VideoAndTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoAndTagService {
    @Autowired
    private VideoAndTagRepository videoAndTagRepository;

    // 1. Add tag to video
    public VideoAndTagDTO addToVideo(VideoAndTagDTO dto) {
        VideoAndTagEntity entity = new VideoAndTagEntity();
        entity.setVideoId(dto.getVideoId());
        entity.setTagId(dto.getTagId());
        videoAndTagRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    // 2. Delete tag from video
    public ApiResponseDTO deleteFromVideo(String videoId, Integer tagId) {
        VideoAndTagEntity entity = videoAndTagRepository.findByVideoIdAndTagId(videoId,tagId).orElseThrow(()->new ItemNotFoundException("Video or tag wrong"));
        videoAndTagRepository.delete(entity);
        return new ApiResponseDTO(true, "Tag delete from video");
    }

    // 3. Get video Tag List by videoId
    public List<VideoAndTagMapper> getTagByVideo(String videoId) {
        List<VideoAndTagEntity> entityList = videoAndTagRepository.getTagByVideo(videoId);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("List empty");
        }
        List<VideoAndTagMapper> mapperList = new ArrayList<>();
        entityList.stream().map(entity -> mapperList.add(toMapper(entity))).collect(Collectors.toList());
        return mapperList;
    }

    private VideoAndTagMapper toMapper(VideoAndTagEntity entity) {
        VideoAndTagMapper mapper = new VideoAndTagMapper();
        mapper.setId(entity.getId());
        mapper.setVideoId(entity.getVideoId());
        mapper.setTag(new TagDTO(entity.getTag().getId(),entity.getTag().getName()));
        mapper.setCreatedDate(entity.getCreatedDate());
        return mapper;
    }

}
