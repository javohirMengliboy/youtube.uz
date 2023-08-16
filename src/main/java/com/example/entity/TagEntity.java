package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "tag")
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "createdDate")
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(mappedBy = "tag")
    private List<VideoAndTagEntity> videoAndTagEntityList;
}
