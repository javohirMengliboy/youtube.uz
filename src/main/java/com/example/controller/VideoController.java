package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.VideoDTO;
import com.example.enums.PlaylistStatus;
import com.example.mapper.VideoShortInfo;
import com.example.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/video")
@Tag(name = "Video",description = "video api list")
public class VideoController {
    @Autowired
    private VideoService videoService;

    // 1. Create Video
    @PostMapping("/create")
    @Operation(summary = "create video", description = "This api used for create video.")
    public ResponseEntity<VideoDTO> create(@RequestBody VideoDTO dto){
        return ResponseEntity.ok().body(videoService.create(dto));
    }

    // 2. Update Video Detail
    @PutMapping("/update/{id}")
    @Operation(summary = "update by id", description = "This api used for update by id.")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable("id") String id,
                                                 @RequestBody VideoDTO dto){
        return ResponseEntity.ok().body(videoService.update(id, dto));
    }

    // 3. Change Video Status
    @PutMapping("/update_status/{id}")
    @Operation(summary = "update status by id", description = "This api used for update status by id.")
    public ResponseEntity<ApiResponseDTO> updateStatus(@PathVariable("id") String id,
                                                       @RequestParam("status") PlaylistStatus status){
        return ResponseEntity.ok().body(videoService.updateStatus(id, status));
    }

    // 4. Increase video_view Count
    @PutMapping("/open/increase_view_count/{id}")
    @Operation(summary = "view count", description = "This api used for view count.")
    public ResponseEntity<VideoShortInfo> increaseViewCount(@PathVariable("id") String id){
        return ResponseEntity.ok().body(videoService.increaseViewCount(id));
    }

    // 5. Get Video Pagination by CategoryId
    @GetMapping("/open/get_page_by_category")
    @Operation(summary = "get page by category", description = "This api used for get page by category.")
    public ResponseEntity<Page<VideoShortInfo>> getPageByCategory(@RequestParam("categoryId") int categoryId,
                                                                  @RequestParam("page") int page,
                                                                  @RequestParam("size") int size){
        return ResponseEntity.ok().body(videoService.getPageByCategory(categoryId, page, size));
    }


    // 6. Search video by Title
    @GetMapping("/open/search_by_title")
    @Operation(summary = "search by title", description = "This api used for search by title.")
    public ResponseEntity<List<VideoShortInfo>> searchByTitle(@RequestParam("title") String title){
        return ResponseEntity.ok().body(videoService.searchByTitle(title));
    }

    // 7. Get video by tag_id with pagination
    @GetMapping("/open/get_page_by_tag")
    @Operation(summary = "get page by tag", description = "This api used for get page by tag.")
    public ResponseEntity<Page<VideoShortInfo>> getPageByTag(@RequestParam("tagId") int tagId,
                                                             @RequestParam("page") int page,
                                                             @RequestParam("size") int size){
        return ResponseEntity.ok().body(videoService.getPageByTag(tagId, page, size));
    }
}
