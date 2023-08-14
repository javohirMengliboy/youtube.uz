package com.example.mapper;

import com.example.enums.PlaylistStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlayListShortInfo {
    private String channelName;
    private String name;
    private PlaylistStatus status;

    public PlayListShortInfo() {
    }

    public PlayListShortInfo(String channelName, String name, PlaylistStatus status) {
        this.channelName = channelName;
        this.name = name;
        this.status = status;
    }
}
