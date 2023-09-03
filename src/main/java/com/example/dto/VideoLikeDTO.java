package com.example.dto;
import com.example.enums.LikeType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class VideoLikeDTO {
    private String id;
    private String profileId;
    private String videoId;
    private LikeType type;
    private LocalDateTime created_date;

    public VideoLikeDTO() {
    }

    public VideoLikeDTO(String id, String profileId, String videoId, LikeType type, LocalDateTime created_date) {
        this.id = id;
        this.profileId = profileId;
        this.videoId = videoId;
        this.type = type;
        this.created_date = created_date;
    }
}
