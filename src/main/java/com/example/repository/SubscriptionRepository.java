package com.example.repository;

import com.example.dto.SubscriptionDTO;
import com.example.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, String> {
    Optional<SubscriptionEntity> findByProfileIdAndChannelId(String profileId, String channelId);

    @Query("select new com.example.dto.SubscriptionDTO(id, profileId, channelId, unsubscribeDate, status, notification_type, createdDate)" +
            " from SubscriptionEntity where profileId = :currentUserId and status = 'ACTIVE' ")
    List<SubscriptionDTO> getUserSubscriptionList(String currentUserId);
}
