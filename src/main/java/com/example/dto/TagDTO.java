package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO {
    private Integer id;
    private String name;
    private LocalDateTime createdDate;

    public TagDTO() {
    }

    public TagDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public TagDTO(Integer id, String name, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
    }
}
