package com.example.mapper;

import com.example.enums.ReportType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportInfoMapper {
    private String id;
    private ProfileForReport profile;
    private String content;
    private String channelId;
    private String videoId;
    private ReportType type;

    public ReportInfoMapper() {
    }

    public ReportInfoMapper(String id, ProfileForReport profile, String content, String channelId, String videoId, ReportType type) {
        this.id = id;
        this.profile = profile;
        this.content = content;
        this.channelId = channelId;
        this.videoId = videoId;
        this.type = type;
    }
}
