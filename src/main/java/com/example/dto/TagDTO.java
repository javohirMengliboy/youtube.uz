package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class TagDTO {
    private UUID id;
    private String name;
    private LocalDateTime createdDate;
}
