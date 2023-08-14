package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.PlaylistDTO;
import com.example.enums.PlaylistStatus;
import com.example.mapper.PlayListShortInfo;
import com.example.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    // 1. Create Playlist
    @PostMapping("/create")
    public ResponseEntity<PlaylistDTO> create(@RequestBody PlaylistDTO dto){
        return ResponseEntity.ok().body(playlistService.create(dto));
    }

    // 2. Update Playlist
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable("id") Integer id,
                                                 @RequestBody PlaylistDTO dto){
        return ResponseEntity.ok().body(playlistService.update(id, dto));
    }

    // 3. Change Playlist
    @PutMapping("/update_status/{id}")
    public ResponseEntity<ApiResponseDTO> updateStatus(@PathVariable("id") Integer id,
                                                       @RequestParam("status") PlaylistStatus status){
        return ResponseEntity.ok().body(playlistService.updateStatus(id, status));
    }

    // 4. Delete Playlist
    @PutMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(playlistService.delete(id));
    }

    // 5. Playlist Pagination
    @PutMapping("/pagination")
    public ResponseEntity<Page<PlaylistDTO>> pagination(@RequestParam("page") int page,
                                                        @RequestParam("size") int size){
        return ResponseEntity.ok().body(playlistService.pagination(page, size));
    }

    // 6. Playlist List By UserId
    @PutMapping("/list_by_user_id")
    public ResponseEntity<List<PlaylistDTO>> getListByUserId(@RequestParam("userId") String userId){
        return ResponseEntity.ok().body(playlistService.getListByUserId(userId));
    }

    // 7. Get User Playlist
    @PutMapping("/get_user_playlist")
    public ResponseEntity<List<PlayListShortInfo>> getUserPlaylist(){
        return ResponseEntity.ok().body(playlistService.getUserPlaylist());
    }
}
