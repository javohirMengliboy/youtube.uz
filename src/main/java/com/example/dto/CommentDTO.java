package com.example.dto;

import com.example.entity.ProfileEntity;
import com.example.entity.VideoEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
    private String id;
    private String profileId;
    private String videoId;
    private String content;
    private String replyId;
    private Integer likeCount;
    private Integer dislikeCount;
    private LocalDateTime createdDate;

    public CommentDTO() {
    }

    public CommentDTO(String id, String profileId, String videoId, String content, String replyId, Integer likeCount, Integer dislikeCount, LocalDateTime createdDate) {
        this.id = id;
        this.profileId = profileId;
        this.videoId = videoId;
        this.content = content;
        this.replyId = replyId;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.createdDate = createdDate;
    }
}
