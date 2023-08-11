package com.example.dto;

import com.example.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private String id;
    private String email;


    public JwtDTO(String id, String email) {
        this.id = id;
        this.email = email;
    }
}
