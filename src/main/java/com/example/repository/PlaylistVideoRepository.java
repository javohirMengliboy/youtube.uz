package com.example.repository;

import com.example.dto.PlaylistVideoDTO;
import com.example.entity.PlaylistVideoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaylistVideoRepository extends CrudRepository<PlaylistVideoEntity, String> {
    @Query("select new com.example.dto.PlaylistVideoDTO(id, playlistId, videoId, orderNumber, createdDate) from PlaylistVideoEntity where playlistId = :playlistId")
    List<PlaylistVideoDTO> getVideoListByPlaylistId(Integer playlistId);
}
