package com.example.entity;

import com.example.enums.PlaylistStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "playlist")
public class PlaylistEtity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "channelId")
    private String channelId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Enumerated
    @Column(name = "status")
    private PlaylistStatus status;
    @Column(name = "orderNum",unique = true)
    private Integer orderNum;
}
