package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class EmailDTO {

    private Integer id;
    private String to_email;
    private String title;
    private String message;
    private LocalDateTime created_date;
}
