package com.example.repository;

import com.example.entity.VideoAndTagEntity;
import com.example.mapper.VideoAndTagMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VideoAndTagRepository extends CrudRepository<VideoAndTagEntity, String> {
    Optional<VideoAndTagEntity> findByVideoIdAndTagId(String videoId, Integer tagId);

    @Query("from VideoAndTagEntity  where videoId = :videoId")
    List<VideoAndTagEntity> getTagByVideo(String videoId);
}
