package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.ChannelDTO;
import com.example.enums.ProfileStatus;
import com.example.mapper.ChannelMapper;
import com.example.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/channel")
@Tag(name = "Channel",description = "channel api list")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    // 1. Create Channel
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    @Operation(summary = "channel create", description = "This api used for create channel.")
    public ResponseEntity<ChannelDTO> create(@RequestBody ChannelDTO dto){
        return ResponseEntity.ok().body(channelService.create(dto));
    }

    // 2. Update Channel
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update_detail/{id}")
    @Operation(summary = "update channel", description = "This api used for update channel by id.")
    public ResponseEntity<ApiResponseDTO> updateDetail(@PathVariable("id") String id,
                                                       @RequestBody ChannelDTO dto){
        return ResponseEntity.ok().body(channelService.updateDetail(id, dto));
    }

    // 3. Update Channel photo
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update_photo/{id}")
    @Operation(summary = "update photo", description = "This api used for update channel photo.")
    public ResponseEntity<ApiResponseDTO> updatePhoto(@PathVariable("id") String id,
                                                       @RequestParam("photoId") String photoId){
        return ResponseEntity.ok().body(channelService.updatePhoto(id, photoId));
    }

    // 4. Update Channel banner
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update_banner/{id}")
    @Operation(summary = "update banner", description = "This api used for update channel banner.")
    public ResponseEntity<ApiResponseDTO> updateBanner(@PathVariable("id") String id,
                                                      @RequestParam("bannerId") String bannerId){
        return ResponseEntity.ok().body(channelService.updateBanner(id, bannerId));
    }

    // 5. Channel Pagination
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/pagination")
    @Operation(summary = "pagination", description = "This api used for channel pagination.")
    public ResponseEntity<Page<ChannelMapper>> pagination(@RequestParam("page") int page,
                                                         @RequestParam("size") int size){
        return ResponseEntity.ok().body(channelService.pagination(page, size));
    }

    // 6. Get Channel By Id
    @GetMapping("/open/get_by_id/{id}")
    @Operation(summary = "get channel by id", description = "This api used for get channel by id.")
    public ResponseEntity<ChannelMapper> getById(@PathVariable("id") String id){
        return ResponseEntity.ok().body(channelService.getById(id));
    }

    // 7. Change Channel Status
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/update_status/{id}")
    @Operation(summary = "update status", description = "This api used for update channel status.")
    public ResponseEntity<ApiResponseDTO> updateStatus(@PathVariable("id") String id,
                                                       @RequestParam("status") ProfileStatus status){
        return ResponseEntity.ok().body(channelService.updateStatus(id, status));
    }

    // 8. User Channel List
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/open/get_channel_list")
    @Operation(summary = "get channel list", description = "This api used for get channel list.")
    public ResponseEntity<List<ChannelMapper>> getChannelList(){
        return ResponseEntity.ok().body(channelService.getChannelList());
    }


}