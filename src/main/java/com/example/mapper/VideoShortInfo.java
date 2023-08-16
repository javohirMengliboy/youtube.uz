package com.example.mapper;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class VideoShortInfo {
    private String previewAttachId;
    private String title;
    private Integer viewCount;
    private String channelId;
    private LocalDateTime publishedDate;

    public VideoShortInfo(String title, Integer viewCount) {
        this.title = title;
        this.viewCount = viewCount;
    }

    public VideoShortInfo(String previewAttachId, String title, Integer viewCount, String channelId, LocalDateTime publishedDate) {
        this.previewAttachId = previewAttachId;
        this.title = title;
        this.viewCount = viewCount;
        this.channelId = channelId;
        this.publishedDate = publishedDate;
    }
}
