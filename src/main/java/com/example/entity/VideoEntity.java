package com.example.entity;

import com.example.enums.PlaylistStatus;
import com.example.enums.VideoType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "video")
public class VideoEntity extends BaseEntity{
    @Column(name = "preview_attach_id")
    private String previewAttachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preview_attach_id", insertable = false, updatable = false)
    private AttachEntity previewAttach;

    @Column()
    private String title;

    @Column(name = "category_id")
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "attach_id")
    private String attachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Enumerated(EnumType.STRING)
    @Column()
    private PlaylistStatus status;

    @Enumerated(EnumType.STRING)
    @Column()
    private VideoType type;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "shared_count")
    private Integer sharedCount;

    @Column()
    private String description;

    @Column(name = "channel_id")
    private String channelId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channel;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "dislike_count")
    private Integer dislikeCount;

    @Column(name = "profile_id")
    private String profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @OneToMany(mappedBy = "video")
    private List<VideoAndTagEntity> videoAndTagList;
}
