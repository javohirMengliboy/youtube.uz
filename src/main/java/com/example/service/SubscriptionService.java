package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.SubscriptionDTO;
import com.example.entity.SubscriptionEntity;
import com.example.enums.SubscriptionStatus;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.SubscriptionRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    // 1. Create User Subscription
    public SubscriptionDTO create(SubscriptionDTO dto) {
        SubscriptionEntity entity = new SubscriptionEntity();
        entity.setChannelId(dto.getChannelId());
        entity.setNotification_type(dto.getNotification_type());
        entity.setProfileId(SpringSecurityUtil.getCurrentUserId());
        entity.setStatus(SubscriptionStatus.ACTIVE);
        subscriptionRepository.save(entity);
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    // 2. Change Subscription Status
    public ApiResponseDTO changeStatus(SubscriptionDTO dto) {
        SubscriptionEntity entity = get(SpringSecurityUtil.getCurrentUserId(), dto.getChannelId());
        entity.setStatus(SubscriptionStatus.BLOCK);
        subscriptionRepository.save(entity);
        return new ApiResponseDTO(true, "Subscription status changed");
    }

    // 3. Change Subscription Notification type
    public ApiResponseDTO changeNotificationType(SubscriptionDTO dto) {
        SubscriptionEntity entity = get(SpringSecurityUtil.getCurrentUserId(), dto.getChannelId());
        entity.setNotification_type(dto.getNotification_type());
        subscriptionRepository.save(entity);
        return new ApiResponseDTO(true, "Subscription notification type changed");
    }

    // 4. Get User Subscription List
    public List<SubscriptionDTO> getUserSubscriptionList() {
        List<SubscriptionDTO> dtoList = subscriptionRepository.getUserSubscriptionList(SpringSecurityUtil.getCurrentUserId());
        if (dtoList.isEmpty()){
            throw new AppBadRequestException("Subscription List is empty");
        }
        return dtoList;
    }

    // 5. Get User Subscription List By UserId
    public List<SubscriptionDTO> getUserSubscriptionListByUserId(String userId) {
        List<SubscriptionDTO> dtoList = subscriptionRepository.getUserSubscriptionList(userId);
        if (dtoList.isEmpty()){
            throw new AppBadRequestException("Subscription List is empty");
        }
        return dtoList;
    }

    // ----------------------------------------------------------------------
    public SubscriptionEntity get(String profileId, String channelId){
        return subscriptionRepository.findByProfileIdAndChannelId(profileId, channelId).orElseThrow(() -> new ItemNotFoundException("Subscription not found"));
    }


}
