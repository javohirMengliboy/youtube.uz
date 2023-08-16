package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoAndTagDTO {
    private String id;
    private String videoId;
    private Integer tagId;
    private LocalDateTime createdDate;

    public VideoAndTagDTO() {
    }

    public VideoAndTagDTO(String id, String videoId, Integer tagId, LocalDateTime createdDate) {
        this.id = id;
        this.videoId = videoId;
        this.tagId = tagId;
        this.createdDate = createdDate;
    }
}
