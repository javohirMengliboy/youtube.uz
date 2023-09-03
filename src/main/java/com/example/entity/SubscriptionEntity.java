package com.example.entity;

import com.example.enums.NotificationType;
import com.example.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "subscription")
public class SubscriptionEntity extends BaseEntity{
    @Column(name = "profile_id")
    private String profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channel;

    @Column(name = "unsubscribe_date")
    private LocalDateTime unsubscribeDate;

    @Column(name = "status")
    private SubscriptionStatus status;

    @Column(name = "notification_type")
    private NotificationType notification_type;
}
