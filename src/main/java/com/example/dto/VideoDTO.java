package com.example.dto;

import com.example.enums.PlaylistStatus;
import com.example.enums.VideoType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class VideoDTO {
    private String id;
    private String previewAttachId;
    private String title;
    private Integer categoryId;
    private String attachId;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private PlaylistStatus status;
    private VideoType type;
    private Integer viewCount;
    private Integer sharedCount;
    private String description;
    private String channelId;
    private Integer likeCount;
    private Integer dislikeCount;
    private String profileId;

    public VideoDTO() {
    }

    public VideoDTO(String id, String previewAttachId, String title, Integer categoryId, String attachId, LocalDateTime createdDate, LocalDateTime publishedDate, PlaylistStatus status, VideoType type, Integer viewCount, Integer sharedCount, String description, String channelId, Integer likeCount, Integer dislikeCount, String profileId) {
        this.id = id;
        this.previewAttachId = previewAttachId;
        this.title = title;
        this.categoryId = categoryId;
        this.attachId = attachId;
        this.createdDate = createdDate;
        this.publishedDate = publishedDate;
        this.status = status;
        this.type = type;
        this.viewCount = viewCount;
        this.sharedCount = sharedCount;
        this.description = description;
        this.channelId = channelId;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.profileId = profileId;
    }
}
