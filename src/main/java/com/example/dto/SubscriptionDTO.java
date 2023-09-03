package com.example.dto;
import com.example.enums.NotificationType;
import com.example.enums.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class SubscriptionDTO {
    private String id;
    private String profileId;
    private String channelId;
    private LocalDateTime unsubscribeDate;
    private SubscriptionStatus status;
    private NotificationType notification_type;
    private LocalDateTime createdDate;

    public SubscriptionDTO() {
    }

    public SubscriptionDTO(String id, String profileId, String channelId, LocalDateTime unsubscribeDate, SubscriptionStatus status, NotificationType notification_type, LocalDateTime createdDate) {
        this.id = id;
        this.profileId = profileId;
        this.channelId = channelId;
        this.unsubscribeDate = unsubscribeDate;
        this.status = status;
        this.notification_type = notification_type;
        this.createdDate = createdDate;
    }
}
