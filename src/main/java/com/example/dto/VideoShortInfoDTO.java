package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VideoShortInfoDTO {
    private String title;
    private Integer viewCount;

    public VideoShortInfoDTO(String title, Integer viewCount) {
        this.title = title;
        this.viewCount = viewCount;
    }
}
