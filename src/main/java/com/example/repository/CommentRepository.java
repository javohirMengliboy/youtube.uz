package com.example.repository;

import com.example.dto.CommentDTO;
import com.example.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity, String> {
    @Query("select new com.example.dto.CommentDTO(id, profileId, videoId, content, replyId, likeCount, dislikeCount, createdDate) from CommentEntity ")
    Page<CommentDTO> getAllPagination(Pageable pageable);

    @Query("select new com.example.dto.CommentDTO(id, profileId, videoId, content, replyId, likeCount, dislikeCount, createdDate)" +
            " from CommentEntity where profileId = :profileId")
    List<CommentDTO> getListByProfileId(String profileId);

    @Query("select new com.example.dto.CommentDTO(id, profileId, videoId, content, replyId, likeCount, dislikeCount, createdDate)" +
            " from CommentEntity where videoId = :videoId")
    List<CommentDTO> getListByVideoId(String videoId);

    @Query("select new com.example.dto.CommentDTO(id, profileId, videoId, content, replyId, likeCount, dislikeCount, createdDate)" +
            " from CommentEntity where replyId = :id")
    List<CommentDTO> getRepliedCommentListByCommentId(String id);
}
