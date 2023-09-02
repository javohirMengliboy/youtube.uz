package com.example.mapper;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class PlaylistFullInfoMapper {
    private Integer id;
    private String name;
    private Long videoCount;
    private Long totalViewCount;
    private LocalDateTime lastUpdateDate;
}
