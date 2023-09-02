package com.example.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachShortInfo {
    private String id;
    private String url;
    private String duration;
}
