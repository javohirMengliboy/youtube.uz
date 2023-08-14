package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.PlaylistDTO;
import com.example.entity.PlaylistEntity;
import com.example.enums.PlaylistStatus;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.PlayListShortInfo;
import com.example.repository.PlaylistRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    // 1. Create Playlist
    public PlaylistDTO create(PlaylistDTO dto) {
        //TODO CHECK
        PlaylistEntity entity = new PlaylistEntity();
        entity.setChannelId(dto.getChannelId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setOrderNumber(dto.getOrderNumber());
        playlistRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    // 2. Update Playlist
    public ApiResponseDTO update(Integer id, PlaylistDTO dto) {
        PlaylistEntity entity = get(id);
        entity.setChannelId(dto.getChannelId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setOrderNumber(dto.getOrderNumber());
        playlistRepository.save(entity);
        return new ApiResponseDTO(true, "Playlist successfully changed");
    }

    private PlaylistEntity get(Integer id) {
        return playlistRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Playlist not found"));
    }

    // 4. Delete Playlist

    // 3. Change Playlist
    public ApiResponseDTO updateStatus(Integer id, PlaylistStatus status) {
        PlaylistEntity entity = get(id);
        entity.setStatus(status);
        playlistRepository.save(entity);
        return new ApiResponseDTO(true, "Playlist status successfully changed");
    }

    // 4. Delete Playlist
    public ApiResponseDTO delete(Integer id) {
        PlaylistEntity entity = get(id);
        playlistRepository.delete(entity);
        return new ApiResponseDTO(true, "Playlist deleted");
    }

    // 5. Playlist Pagination
    public Page<PlaylistDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PlaylistDTO> playlistPage = playlistRepository.getPlaylistPage(pageable);
        if (playlistPage.isEmpty()){
            throw new ItemNotFoundException("Playlist page not found");
        }
        return playlistPage;
    }

    // 6. Playlist List By UserId
    public List<PlaylistDTO> getListByUserId(String userId) {
        List<PlaylistDTO> playlistList = playlistRepository.getListByUserId(userId);
        if (playlistList.isEmpty()){
            throw new ItemNotFoundException("Playlist list not found");
        }
        return playlistList;
    }

    // 7. Get User Playlist
    public List<PlayListShortInfo> getUserPlaylist() {
        List<PlayListShortInfo> playlistList = playlistRepository.getUserPlaylist(SpringSecurityUtil.getCurrentUserId());
        if (playlistList.isEmpty()){
            throw new ItemNotFoundException("Playlist list not found");
        }
        return playlistList;
    }
}
