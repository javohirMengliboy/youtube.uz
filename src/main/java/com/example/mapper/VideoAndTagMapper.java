package com.example.mapper;

import com.example.dto.TagDTO;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class VideoAndTagMapper {
    private String id;
    private String videoId;
    private TagDTO tag;
    private LocalDateTime createdDate;

    public VideoAndTagMapper() {
    }

    public VideoAndTagMapper(String id, String videoId, TagDTO tag, LocalDateTime createdDate) {
        this.id = id;
        this.videoId = videoId;
        this.tag = tag;
        this.createdDate = createdDate;
    }
}
