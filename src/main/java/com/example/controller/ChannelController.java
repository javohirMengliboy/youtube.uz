package com.example.controller;

import com.example.dto.ChannelDTO;
import com.example.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @PostMapping("/create")
    public ResponseEntity<ChannelDTO> create(@RequestBody ChannelDTO dto){
        return ResponseEntity.ok().body(channelService.create(dto));
    }


}