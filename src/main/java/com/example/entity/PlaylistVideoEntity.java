package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "playlist_and_video")
public class PlaylistVideoEntity extends BaseEntity{
    @Column(name = "playlist_id")
    private Integer playlistId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id",insertable = false, updatable = false)
    private PlaylistEntity playlist;

    @Column(name = "video_id")
    private String videoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", insertable = false,updatable = false)
    private VideoEntity video;

    @Column(name = "order_number")
    private Integer orderNumber;
}
