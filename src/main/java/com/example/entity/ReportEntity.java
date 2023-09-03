package com.example.entity;

import com.example.enums.ReportType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "report")
public class ReportEntity extends BaseEntity{
    @Column(name = "profile_id")
    private String profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "content")
    private String content;

    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channel;

    @Column(name = "video_id")
    private String videoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", insertable = false, updatable = false)
    private VideoEntity video;

    @Column(name = "type")
    private ReportType type;
}
