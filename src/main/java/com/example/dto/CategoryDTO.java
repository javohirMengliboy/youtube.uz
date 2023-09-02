package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    private Integer id;
    private String name;
    private LocalDateTime createdDate;

}
