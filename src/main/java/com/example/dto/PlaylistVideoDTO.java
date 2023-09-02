package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class PlaylistVideoDTO {
    private String id;
    private Integer playlistId;
    private String videoId;
    private Integer orderNumber;
    private LocalDateTime createdDate;

    public PlaylistVideoDTO() {
    }

    public PlaylistVideoDTO(String id, Integer playlistId, String videoId, Integer orderNumber, LocalDateTime createdDate) {
        this.id = id;
        this.playlistId = playlistId;
        this.videoId = videoId;
        this.orderNumber = orderNumber;
        this.createdDate = createdDate;
    }
}
