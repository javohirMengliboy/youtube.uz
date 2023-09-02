package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    private String id;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "size")
    private Long size;

    @Column(name = "type")
    private String type;

    @Column(name = "path")
    private String path;

    @Column(name = "duration")
    private String duration;

    @Column(name = "extension")
    private String extension;

    @OneToOne(mappedBy = "previewAttach")
    private VideoEntity videoImg;

    @OneToOne(mappedBy = "attach")
    private VideoEntity video;
}
