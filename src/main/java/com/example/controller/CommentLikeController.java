package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.VideoLikeDTO;
import com.example.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/commentLike")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    // 1. Create Video like
    @PutMapping("/like")
    public ResponseEntity<ApiResponseDTO> like(@RequestParam("commentId") String videoId){
        return ResponseEntity.ok().body(commentLikeService.like(videoId));
    }

    // 2. Remove Video Like
    @PutMapping("/dislike")
    public ResponseEntity<ApiResponseDTO> dislike(@RequestParam("commentId") String videoId){
        return ResponseEntity.ok().body(commentLikeService.dislike(videoId));
    }

    // 3. User Liked Video List
    @GetMapping("/get_liked_video_list_for_user")
    public ResponseEntity<List<VideoLikeDTO>> getLikedVideoListForUser(){
        return ResponseEntity.ok().body(commentLikeService.getLikedVideoListForUser());
    }

    // 4. Get User LikedVideo List By UserId
    @GetMapping("/get_liked_video_list_user_by_user_id")
    public ResponseEntity<List<VideoLikeDTO>> getLikedVideoListByUserId(@RequestParam("userId") String userId){
        return ResponseEntity.ok().body(commentLikeService.getLikedVideoListByUserId(userId));
    }
}
