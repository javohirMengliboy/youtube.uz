package com.example.config;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
public class SpringSecurityConfig {
    private UUID id;
    private String name;
    private LocalDateTime createdDate;
}
