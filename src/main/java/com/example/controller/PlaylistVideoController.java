package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.PlaylistVideoDTO;
import com.example.service.PlaylistVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playlistVideo")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class PlaylistVideoController {
    @Autowired
    private PlaylistVideoService playlistVideoService;

    @PostMapping("/create")
    public ResponseEntity<PlaylistVideoDTO> create(@RequestBody PlaylistVideoDTO dto){
        return ResponseEntity.ok().body(playlistVideoService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO> update(@RequestBody PlaylistVideoDTO dto,
                                                 @PathVariable("id") String id){
        return ResponseEntity.ok().body(playlistVideoService.update(dto,id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable("id") String id){
        return ResponseEntity.ok().body(playlistVideoService.delete(id));
    }

    @GetMapping("/get_video_list_by_playlist_id")
    public ResponseEntity<List<PlaylistVideoDTO>> getVideoListByPlaylistId(@RequestParam("playlistId") Integer playlistId){
        return ResponseEntity.ok().body(playlistVideoService.getVideoListByPlaylistId(playlistId));
    }
}
