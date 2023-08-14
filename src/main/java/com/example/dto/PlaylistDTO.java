package com.example.dto;

import com.example.enums.PlaylistStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlaylistDTO {
    private Integer id;
    private String channelId;
    private String name;
    private String description;
    private PlaylistStatus status;
    private Integer orderNumber;

    public PlaylistDTO() {
    }

    public PlaylistDTO(Integer id, String channelId, String name, String description, PlaylistStatus status, Integer orderNumber) {
        this.id = id;
        this.channelId = channelId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.orderNumber = orderNumber;
    }
}
