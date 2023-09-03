package com.example.dto;
import com.example.enums.ReportType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ReportDTO {
    private String id;
    private String profileId;
    private String content;
    private String channelId;
    private String videoId;
    private ReportType type;
    private LocalDateTime createdDate;

    public ReportDTO() {
    }

    public ReportDTO(String id, String profileId, String content, String channelId, String videoId, ReportType type, LocalDateTime createdDate) {
        this.id = id;
        this.profileId = profileId;
        this.content = content;
        this.channelId = channelId;
        this.videoId = videoId;
        this.type = type;
        this.createdDate = createdDate;
    }
}
