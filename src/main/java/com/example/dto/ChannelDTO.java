package com.example.dto;

import com.example.entity.AttachEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ChannelDTO {
    private String id;
    private LocalDateTime createdDate = LocalDateTime.now();
    private String name;
    private String photoId;
    private String description;
    private ProfileStatus status;
    private String bannerId;
    private String profileId;

    public ChannelDTO() {
    }

    public ChannelDTO(String id, LocalDateTime createdDate, String name, String photoId, String description, ProfileStatus status, String bannerId, String profileId) {
        this.id = id;
        this.createdDate = createdDate;
        this.name = name;
        this.photoId = photoId;
        this.description = description;
        this.status = status;
        this.bannerId = bannerId;
        this.profileId = profileId;
    }
}
