package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.PlaylistDTO;
import com.example.entity.PlaylistEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.PlaylistStatus;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.PlayListShortInfo;
import com.example.mapper.PlaylistFullInfoMapper;
import com.example.mapper.PlaylistFullInfoMapperI;
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
        ProfileEntity profile = getOwner(entity.getId());
        if (!SpringSecurityUtil.getCurrentUserId().equals(profile.getId())){
            throw new AppBadRequestException("Can't change this playlist");
        }
        entity.setChannelId(dto.getChannelId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setOrderNumber(dto.getOrderNumber());
        playlistRepository.save(entity);
        return new ApiResponseDTO(true, "Playlist successfully changed");
    }

    // 3. Change Playlist
    public ApiResponseDTO updateStatus(Integer id, PlaylistStatus status) {
        PlaylistEntity entity = get(id);
        ProfileEntity profile = getOwner(entity.getId());
        if (!SpringSecurityUtil.getCurrentUserId().equals(profile.getId())){
            throw new AppBadRequestException("Can't change status this playlist");
        }
        entity.setStatus(status);
        playlistRepository.save(entity);
        return new ApiResponseDTO(true, "Playlist status successfully changed");
    }

    // 4. Delete Playlist
    public ApiResponseDTO delete(Integer id) {
        PlaylistEntity entity = get(id);
        ProfileEntity owner = getOwner(entity.getId());
        ProfileEntity currentUser = SpringSecurityUtil.getCurrentUser().getProfile();
        if (currentUser.getRole().equals(ProfileRole.ROLE_USER) && !currentUser.getId().equals(owner.getId())){
            throw new AppBadRequestException("Can't delete this playlist");
        }
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

    // 8. Get Channel Play List By ChannelKey
    public List<PlayListShortInfo> getChannelPlaylist(String channelId) {
        List<PlayListShortInfo> playlistList = playlistRepository.getChannelPlaylist(channelId);
        if (playlistList.isEmpty()){
            throw new ItemNotFoundException("Playlist list not found");
        }
        return playlistList;
    }

    // 9. Get Playlist by id
    public PlaylistFullInfoMapper getPlaylistById(Integer playlistId) {
        PlaylistFullInfoMapperI object = playlistRepository.getPlaylistById(playlistId);
        if (object == null){
            throw new ItemNotFoundException("Playlist list not found");
        }
        PlaylistFullInfoMapper fullInfoMapper = new PlaylistFullInfoMapper();
        fullInfoMapper.setId(object.getId());
        fullInfoMapper.setName(object.getName());
        fullInfoMapper.setVideoCount(object.getVideoCount());
        fullInfoMapper.setTotalViewCount(object.getTotalViewCount());
        fullInfoMapper.setLastUpdateDate(object.getLastUpdateDate());
        return fullInfoMapper;
    }

    //----------------------------------------------
    private PlaylistEntity get(Integer id) {
        return playlistRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Playlist not found"));
    }

    public ProfileEntity getOwner(Integer playlistId){
        return playlistRepository.getOwner(playlistId);
    }

}
