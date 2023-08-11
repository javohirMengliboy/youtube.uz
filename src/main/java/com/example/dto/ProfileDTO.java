package com.example.dto;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String photoId;
    private ProfileRole role;
    private ProfileStatus status;
    private LocalDateTime createdDate;

    public ProfileDTO() {
    }

    public ProfileDTO(String id, String name, String surname, String email, String photoId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.photoId = photoId;
    }
}
