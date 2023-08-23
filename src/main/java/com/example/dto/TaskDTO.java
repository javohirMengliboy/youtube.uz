package com.example.dto;

import com.example.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TaskDTO {
    private String id;
    private String title;
    private String content;
    private TaskStatus status;
}

