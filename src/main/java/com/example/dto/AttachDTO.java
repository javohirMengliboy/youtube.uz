package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {

    private String id;
    private String origin_name;
    private Long size;
    private String type;
    private String path;
    private String extension;
    private String duration;
    private String url;

}
