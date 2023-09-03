package com.example.mapper;

import com.example.enums.ReportType;

public interface ReportInfoI {
    String getId();
    String getProfileId();
    String getName();
    String getSurname();
    String getPhotoId();
    String getUrl();
    String getContent();
    String getChannelId();
    String getVideoId();
    ReportType getType();
}
