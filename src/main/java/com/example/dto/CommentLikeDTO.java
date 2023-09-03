package com.example.dto;

import com.example.enums.LikeType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentLikeDTO {
    private String id;
    private String profileId;
    private String commentId;
    private LikeType type;
    private LocalDateTime created_date;

    public CommentLikeDTO() {
    }

    public CommentLikeDTO(String id, String profileId, String commentId, LikeType type, LocalDateTime created_date) {
        this.id = id;
        this.profileId = profileId;
        this.commentId = commentId;
        this.type = type;
        this.created_date = created_date;
    }
}
