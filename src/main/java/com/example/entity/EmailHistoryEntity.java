package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "email")
public class EmailHistoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(name = "to_email")
    private String to_email;

    @Column(name = "title",columnDefinition = "TEXT")
    private String title;

    @Column(name = "message",columnDefinition = "TEXT")
    private String message;

    @Column(name = "created_date")
    private LocalDateTime created_date = LocalDateTime.now();


}
