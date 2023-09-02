package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.PlaylistVideoDTO;
import com.example.entity.PlaylistVideoEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.PlaylistVideoRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistVideoService {
    @Autowired
    private PlaylistVideoRepository playlistVideoRepository;

    @Autowired
    private VideoService videoService;

    public PlaylistVideoDTO create(PlaylistVideoDTO dto) {
        String ownerId = videoService.getOwnerId(dto.getVideoId());
        System.out.println("Owner --- "+ownerId);
        System.out.println("Current user --- "+SpringSecurityUtil.getCurrentUserId());
        if (!ownerId.equals(SpringSecurityUtil.getCurrentUserId())){
            throw new AppBadRequestException("You don't owner");
        }
        PlaylistVideoEntity entity = new PlaylistVideoEntity();
        entity.setPlaylistId(dto.getPlaylistId());
        entity.setVideoId(dto.getVideoId());
        entity.setOrderNumber(dto.getOrderNumber());
        playlistVideoRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ApiResponseDTO update(PlaylistVideoDTO dto, String id) {
        PlaylistVideoEntity entity = get(id);
        entity.setPlaylistId(dto.getPlaylistId());
        entity.setVideoId(dto.getVideoId());
        entity.setOrderNumber(dto.getOrderNumber());
        playlistVideoRepository.save(entity);
        return new ApiResponseDTO(true, "PlaylistVideo Changed");
    }

    public ApiResponseDTO delete(String id) {
        playlistVideoRepository.deleteById(id);
        return null;
    }

    public List<PlaylistVideoDTO> getVideoListByPlaylistId(Integer playlistId) {
        List<PlaylistVideoDTO> dtoList = playlistVideoRepository.getVideoListByPlaylistId(playlistId);
        if (dtoList.isEmpty()){
            throw new AppBadRequestException("PlaylistVideo list empty");
        }
        return dtoList;
    }

    //---------------------------------------------------------

    public PlaylistVideoEntity get(String id){
        return playlistVideoRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("PlaylistVideo not found"));
    }
}
