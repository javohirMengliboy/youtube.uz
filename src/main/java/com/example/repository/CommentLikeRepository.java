package com.example.repository;

import com.example.dto.CommentLikeDTO;
import com.example.dto.VideoLikeDTO;
import com.example.entity.CommentLikeEntity;
import com.example.entity.VideoLikeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, String> {
    CommentLikeEntity findByCommentIdAndProfileId(String commentId, String profileId);

    @Query("select new com.example.dto.CommentLikeDTO(id, profileId, commentId, type, createdDate) from CommentLikeEntity where profileId = :currentUserId")
    List<CommentLikeDTO> getVideoListLikedForUser(String currentUserId);
}
