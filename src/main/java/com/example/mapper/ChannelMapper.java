package com.example.mapper;

import com.example.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChannelMapper {
    private String name;
    private String photoId;
    private String description;
    private ProfileStatus status;

    public ChannelMapper() {
    }

    public ChannelMapper(String name, String photoId, String description, ProfileStatus status) {
        this.name = name;
        this.photoId = photoId;
        this.description = description;
        this.status = status;
    }
}
