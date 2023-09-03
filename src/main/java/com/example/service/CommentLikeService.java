package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.VideoLikeDTO;
import com.example.entity.CommentLikeEntity;
import com.example.enums.LikeType;
import com.example.exp.AppBadRequestException;
import com.example.repository.CommentLikeRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;

    // 1. Create Video like
    public ApiResponseDTO like(String commentId) {
        String currentUserId = SpringSecurityUtil.getCurrentUserId();
        CommentLikeEntity likeEntity = commentLikeRepository.findByCommentIdAndProfileId(commentId, currentUserId);
        if (likeEntity == null){
            CommentLikeEntity entity = new CommentLikeEntity();
            entity.setCommentId(commentId);
            entity.setProfileId(currentUserId);
            entity.setType(LikeType.LIKE);
            commentLikeRepository.save(entity);
            return new ApiResponseDTO(true, "Liked");
        }else {
            if (likeEntity.getType().equals(LikeType.LIKE)){
                commentLikeRepository.delete(likeEntity);
                return new ApiResponseDTO(true, "Removed like");
            }else {
                likeEntity.setType(LikeType.LIKE);
                commentLikeRepository.save(likeEntity);
                return new ApiResponseDTO(true, "Removed Dislike and Liked");
            }
        }
    }

    // 2. Remove Video Like
    public ApiResponseDTO dislike(String commentId) {
        String currentUserId = SpringSecurityUtil.getCurrentUserId();
        CommentLikeEntity dislikeEntity = commentLikeRepository.findByCommentIdAndProfileId(commentId, currentUserId);
        if (dislikeEntity == null){
            CommentLikeEntity entity = new CommentLikeEntity();
            entity.setCommentId(commentId);
            entity.setProfileId(currentUserId);
            entity.setType(LikeType.DISLIKE);
            commentLikeRepository.save(entity);
            return new ApiResponseDTO(true, "Disliked");
        }else {
            if (dislikeEntity.getType().equals(LikeType.DISLIKE)){
                commentLikeRepository.delete(dislikeEntity);
                return new ApiResponseDTO(true, "Removed dislike");
            }else {
                dislikeEntity.setType(LikeType.DISLIKE);
                commentLikeRepository.save(dislikeEntity);
                return new ApiResponseDTO(true, "Removed like and Disliked");
            }
        }
    }

    // 3. User Liked Video List
    public List<VideoLikeDTO> getLikedVideoListForUser() {
        List<VideoLikeDTO> dtoList = commentLikeRepository.getVideoListLikedForUser(SpringSecurityUtil.getCurrentUserId());
        if (dtoList.isEmpty()){
            throw new AppBadRequestException("Liked video not found");
        }
        return dtoList;
    }

    // 4. Get User LikedVideo List By UserId
    public List<VideoLikeDTO> getLikedVideoListByUserId(String userId) {
        List<VideoLikeDTO> dtoList = commentLikeRepository.getVideoListLikedForUser(userId);
        if (dtoList.isEmpty()){
            throw new AppBadRequestException("Liked video not found");
        }
        return dtoList;
    }
}
