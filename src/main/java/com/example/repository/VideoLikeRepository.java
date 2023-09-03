package com.example.repository;

import com.example.dto.VideoLikeDTO;
import com.example.entity.VideoLikeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoLikeRepository extends CrudRepository<VideoLikeEntity, String> {
    VideoLikeEntity findByVideoIdAndProfileId(String videoId, String profileId);

    @Query("select new com.example.dto.VideoLikeDTO(id, profileId, videoId, type, createdDate) from VideoLikeEntity where profileId = :currentUserId")
    List<VideoLikeDTO> getVideoListLikedForUser(String currentUserId);
}
