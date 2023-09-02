package com.example.mapper;

import com.example.dto.CategoryDTO;
import com.example.dto.ChannelDTO;
import com.example.dto.LikeTDO;
import com.example.dto.TagDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class VideoFullInfoMapper {
    private String id;
    private String title;
    private String description;
    private AttachShortInfo previewAttach;
    private AttachShortInfo attach;
    private CategoryDTO category;
    private List<TagDTO> tagList;
    private LocalDateTime publishedDate;
    private ChannelDTO channel;
    private Integer viewCount;
    private Integer sharedCount;
    private LikeTDO likeTDO;
    private String duration;
}
