package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.VideoLikeDTO;
import com.example.entity.VideoLikeEntity;
import com.example.enums.LikeType;
import com.example.exp.AppBadRequestException;
import com.example.repository.VideoLikeRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoLikeService {
    @Autowired
    private VideoLikeRepository videoLikeRepository;

    // 1. Create Video like
    public ApiResponseDTO like(String videoId) {
        String currentUserId = SpringSecurityUtil.getCurrentUserId();
        VideoLikeEntity likeEntity = videoLikeRepository.findByVideoIdAndProfileId(videoId, currentUserId);
        if (likeEntity == null){
            VideoLikeEntity entity = new VideoLikeEntity();
            entity.setVideoId(videoId);
            entity.setProfileId(currentUserId);
            entity.setType(LikeType.LIKE);
            videoLikeRepository.save(entity);
            return new ApiResponseDTO(true, "Liked");
        }else {
            if (likeEntity.getType().equals(LikeType.LIKE)){
                videoLikeRepository.delete(likeEntity);
                return new ApiResponseDTO(true, "Removed like");
            }else {
                likeEntity.setType(LikeType.LIKE);
                videoLikeRepository.save(likeEntity);
                return new ApiResponseDTO(true, "Removed Dislike and Liked");
            }
        }
    }

    // 2. Remove Video Like
    public ApiResponseDTO dislike(String videoId) {
        String currentUserId = SpringSecurityUtil.getCurrentUserId();
        VideoLikeEntity dislikeEntity = videoLikeRepository.findByVideoIdAndProfileId(videoId, currentUserId);
        if (dislikeEntity == null){
            VideoLikeEntity entity = new VideoLikeEntity();
            entity.setVideoId(videoId);
            entity.setProfileId(currentUserId);
            entity.setType(LikeType.DISLIKE);
            videoLikeRepository.save(entity);
            return new ApiResponseDTO(true, "Disliked");
        }else {
            if (dislikeEntity.getType().equals(LikeType.DISLIKE)){
                videoLikeRepository.delete(dislikeEntity);
                return new ApiResponseDTO(true, "Removed dislike");
            }else {
                dislikeEntity.setType(LikeType.DISLIKE);
                videoLikeRepository.save(dislikeEntity);
                return new ApiResponseDTO(true, "Removed like and Disliked");
            }
        }
    }

    // 3. User Liked Video List
    public List<VideoLikeDTO> getLikedVideoListForUser() {
        List<VideoLikeDTO> dtoList = videoLikeRepository.getVideoListLikedForUser(SpringSecurityUtil.getCurrentUserId());
        if (dtoList.isEmpty()){
            throw new AppBadRequestException("Liked video not found");
        }
        return dtoList;
    }

    // 4. Get User LikedVideo List By UserId
    public List<VideoLikeDTO> getLikedVideoListByUserId(String userId) {
        List<VideoLikeDTO> dtoList = videoLikeRepository.getVideoListLikedForUser(userId);
        if (dtoList.isEmpty()){
            throw new AppBadRequestException("Liked video not found");
        }
        return dtoList;
    }
}
