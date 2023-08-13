package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FilterResultDTO<T> {
    private List<T> content;
    private Long totalCount;

    public FilterResultDTO(List<T> content, Long totalCount) {
        this.content = content;
        this.totalCount = totalCount;
    }

}
