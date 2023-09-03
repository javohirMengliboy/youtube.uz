package com.example.entity;

import com.example.enums.LikeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comment_like")
public class CommentLikeEntity extends BaseEntity{
    @Column(name = "profile_id")
    private String profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "comment_id")
    private String commentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
    private CommentEntity comment;

    @Column(name = "type")
    private LikeType type;
}
