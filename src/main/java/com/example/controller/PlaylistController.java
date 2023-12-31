package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.PlaylistDTO;
import com.example.enums.PlaylistStatus;
import com.example.mapper.PlayListShortInfo;
import com.example.service.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playlist")
@Tag(name = "Playlist",description = "playlist api list")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    // 1. Create Playlist
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    @Operation(summary = "playlist create",description = "This api used for create playlist")
    public ResponseEntity<PlaylistDTO> create(@RequestBody PlaylistDTO dto){
        return ResponseEntity.ok().body(playlistService.create(dto));
    }

    // 2. Update Playlist
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update/{id}")
    @Operation(summary = "update playlist by id",description = "This api used for update playlist by id")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable("id") Integer id,
                                                 @RequestBody PlaylistDTO dto){
        return ResponseEntity.ok().body(playlistService.update(id, dto));
    }

    // 3. Change Playlist
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update_status/{id}")
    @Operation(summary = "update playlist status by id",description = "This api used for update playlist status by id")
    public ResponseEntity<ApiResponseDTO> updateStatus(@PathVariable("id") Integer id,
                                                       @RequestParam("status") PlaylistStatus status){
        return ResponseEntity.ok().body(playlistService.updateStatus(id, status));
    }

    // 4. Delete Playlist
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/delete/{id}")
    @Operation(summary = "delete playlist",description = "This api used for delete playlist by id")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(playlistService.delete(id));
    }

    // 5. Playlist Pagination
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/pagination")
    @Operation(summary = "playlist pagination",description = "This api used for pagination playlist")
    public ResponseEntity<Page<PlaylistDTO>> pagination(@RequestParam("page") int page,
                                                        @RequestParam("size") int size){
        return ResponseEntity.ok().body(playlistService.pagination(page, size));
    }

    // 6. Playlist List By UserId
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/list_by_user_id")
    @Operation(summary = "list by user id",description = "This api used for listed by user id")
    public ResponseEntity<List<PlaylistDTO>> getListByUserId(@RequestParam("userId") String userId){
        return ResponseEntity.ok().body(playlistService.getListByUserId(userId));
    }

    // 7. Get User Playlist
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/get_user_playlist")
    @Operation(summary = "get user playlist", description = "This api used for get user playlist")
    public ResponseEntity<List<PlayListShortInfo>> getUserPlaylist(){
        return ResponseEntity.ok().body(playlistService.getUserPlaylist());
    }
}
