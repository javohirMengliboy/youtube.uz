package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.CommentDTO;
import com.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // 1. Crate Comment
    @PostMapping("/create")
    public ResponseEntity<CommentDTO> create(@RequestBody CommentDTO dto){
        return ResponseEntity.ok().body(commentService.create(dto));
    }

    // 2. Update Comment
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable("id") String id,
                                                 @RequestBody CommentDTO dto){
        return ResponseEntity.ok().body(commentService.update(id, dto));
    }

    // 3. Delete Comment
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable("id") String id){
        return ResponseEntity.ok().body(commentService.delete(id));
    }

    // 4. Comment List Pagination
    @GetMapping("/get_all_pagination")
    public ResponseEntity<Page<CommentDTO>> getAllPagination(@RequestParam("page") int page,
                                                             @RequestParam("size") int size){
        return ResponseEntity.ok().body(commentService.getAllPagination(page, size));
    }

    // 5. Comment List By profileId
    @GetMapping("/get_list_by_profile_id")
    public ResponseEntity<List<CommentDTO>> getListByProfileId(@RequestParam("profileId") String profileId) {
        return ResponseEntity.ok().body(commentService.getListByProfileId(profileId));
    }

    // 6. Comment List By Profile
    @GetMapping("/get_list_by_profile")
    public ResponseEntity<List<CommentDTO>> getListByProfile() {
        return ResponseEntity.ok().body(commentService.getListByProfile());
    }

    // 7. Comment List by videoId
    @GetMapping("/get_list_by_video_id")
    public ResponseEntity<List<CommentDTO>> getListByVideoId(@RequestParam("videoId") String videoId) {
        return ResponseEntity.ok().body(commentService.getListByVideoId(videoId));
    }

    // 8. Get Comment Replied Comment by comment Id
    @GetMapping("/get_replied_comment_list_by_comment_id/{id}")
    public ResponseEntity<List<CommentDTO>> getRepliedCommentListByCommentId(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(commentService.getRepliedCommentListByCommentId(id));
    }

}
