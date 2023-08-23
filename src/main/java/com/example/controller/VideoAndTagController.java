package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.VideoAndTagDTO;
import com.example.mapper.VideoAndTagMapper;
import com.example.service.VideoAndTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/video_and_tag")
@Tag(name = "Video and tag",description = "videa and tag api list")
public class VideoAndTagController {
    @Autowired
    private VideoAndTagService videoAndTagService;
    // 1. Add tag to video
    @PostMapping("/add_to_video")
    @Operation(summary = "add video",description = "This api used for add video")
    public ResponseEntity<VideoAndTagDTO> addToVideo(@RequestBody VideoAndTagDTO dto){
        return ResponseEntity.ok().body(videoAndTagService.addToVideo(dto));
    }

    // 2. Delete tag from video
    @PutMapping("/delete_from_video")
    @Operation(summary = "delete video",description = "This api used for delete video")
    public ResponseEntity<ApiResponseDTO> deleteFromVideo(@RequestParam("videoId") String videoId,
                                                          @RequestParam("tagId") Integer tagId){
        return ResponseEntity.ok().body(videoAndTagService.deleteFromVideo(videoId, tagId));
    }

    // 3. Get video Tag List by videoId
    @GetMapping("/get_tag_by_video")
    @Operation(summary = "get tag by video",description = "This api used for get tag by video")
    public ResponseEntity<List<VideoAndTagMapper>> getTagByVideo(@RequestParam("videoId") String videoId){
        return ResponseEntity.ok().body(videoAndTagService.getTagByVideo(videoId));
    }
}
