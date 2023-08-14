package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.ChannelDTO;
import com.example.dto.VideoDTO;
import com.example.dto.VideoShortInfoDTO;
import com.example.enums.PlaylistStatus;
import com.example.mapper.VideoShortInfo;
import com.example.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    // 1. Create Video
    @PostMapping("/create")
    public ResponseEntity<VideoDTO> create(@RequestBody VideoDTO dto){
        return ResponseEntity.ok().body(videoService.create(dto));
    }

    // 2. Update Video Detail
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable("id") String id,
                                                 @RequestBody VideoDTO dto){
        return ResponseEntity.ok().body(videoService.update(id, dto));
    }

    // 3. Change Video Status
    @PutMapping("/update_status/{id}")
    public ResponseEntity<ApiResponseDTO> updateStatus(@PathVariable("id") String id,
                                                       @RequestParam("status") PlaylistStatus status){
        return ResponseEntity.ok().body(videoService.updateStatus(id, status));
    }

    // 4. Increase video_view Count
    @PutMapping("/increase_view_count/{id}")
    public ResponseEntity<VideoShortInfo> increaseViewCount(@PathVariable("id") String id){
        return ResponseEntity.ok().body(videoService.increaseViewCount(id));
    }

    // 5. Get Video Pagination by CategoryId
    @GetMapping("/get_page_by_category")
    public ResponseEntity<Page<VideoShortInfo>> getPageByCategory(@RequestParam("categoryId") int categoryId,
                                                                  @RequestParam("page") int page,
                                                                  @RequestParam("size") int size){
        return ResponseEntity.ok().body(videoService.getPageByCategory(categoryId, page, size));
    }


    // 6. Search video by Title
    @GetMapping("/search_by_title")
    public ResponseEntity<List<VideoShortInfo>> searchByTitle(@RequestParam("title") String title){
        return ResponseEntity.ok().body(videoService.searchByTitle(title));
    }

    // 7. Get video by tag_id with pagination
    @GetMapping("/get_page_by_tag")
    public ResponseEntity<Page<VideoShortInfo>> getPageByTag(@RequestParam("tagId") int tagId,
                                                             @RequestParam("page") int page,
                                                             @RequestParam("size") int size){
        return ResponseEntity.ok().body(videoService.getPageByTag(tagId, page, size));
    }
}
