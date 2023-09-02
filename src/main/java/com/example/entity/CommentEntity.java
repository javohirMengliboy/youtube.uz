package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity extends BaseEntity{
    @Column(name = "profile_id")
    private String profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "video_id")
    private String videoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", insertable = false, updatable = false)
    private VideoEntity video;

    @Column(name = "content")
    private String content;

    @Column(name = "reply_id")
    private String replyId;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "dislike_count")
    private Integer dislikeCount;
}
