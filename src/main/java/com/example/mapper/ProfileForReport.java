package com.example.mapper;

import com.example.dto.PhotoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileForReport {
    private String id;
    private String name;
    private String surname;
    private PhotoDTO photo;
}
