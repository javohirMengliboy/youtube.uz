package com.example.entity;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseEntity{
    @Column()
    private String name;

    @Column()
    private String surname;

    @Column(unique = true)
    private String email;

    @Column()
    private String password;

    @Column(name = "photo_id")
    private String photoId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;


    @Enumerated(EnumType.STRING)
    @Column()
    private ProfileRole role;

    @Enumerated(EnumType.STRING)
    @Column()
    private ProfileStatus status;

}
