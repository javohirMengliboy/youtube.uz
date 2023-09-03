package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.VideoLikeDTO;
import com.example.service.VideoLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/videoLike")
public class VideoLikeController {
    @Autowired
    private VideoLikeService videoLikeService;

    // 1. Create Video like
    @PutMapping("/like")
    public ResponseEntity<ApiResponseDTO> like(@RequestParam("videoId") String videoId){
        return ResponseEntity.ok().body(videoLikeService.like(videoId));
    }

    // 2. Remove Video Like
    @PutMapping("/dislike")
    public ResponseEntity<ApiResponseDTO> dislike(@RequestParam("videoId") String videoId){
        return ResponseEntity.ok().body(videoLikeService.dislike(videoId));
    }

    // 3. User Liked Video List
    @GetMapping("/get_liked_video_list_for_user")
    public ResponseEntity<List<VideoLikeDTO>> getLikedVideoListForUser(){
        return ResponseEntity.ok().body(videoLikeService.getLikedVideoListForUser());
    }

    // 4. Get User LikedVideo List By UserId
    @GetMapping("/get_liked_video_list_user_by_user_id")
    public ResponseEntity<List<VideoLikeDTO>> getLikedVideoListByUserId(@RequestParam("userId") String userId){
        return ResponseEntity.ok().body(videoLikeService.getLikedVideoListByUserId(userId));
    }

}
