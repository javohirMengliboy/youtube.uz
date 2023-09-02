package com.example.mapper;

import java.time.LocalDateTime;
public interface PlaylistFullInfoMapperI {
    Integer getId();
    String getName();
    Long getVideoCount();
    Long getTotalViewCount();
    LocalDateTime getLastUpdateDate();


}
