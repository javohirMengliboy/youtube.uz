package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.SubscriptionDTO;
import com.example.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    // 1. Create User Subscription
    @PostMapping("/create")
    public ResponseEntity<SubscriptionDTO> create(@RequestBody SubscriptionDTO dto){
        return ResponseEntity.ok().body(subscriptionService.create(dto));
    }

    // 2. Change Subscription Status
    @PutMapping("/change_status")
    public ResponseEntity<ApiResponseDTO> changeStatus(@RequestBody SubscriptionDTO dto){
        return ResponseEntity.ok().body(subscriptionService.changeStatus(dto));
    }

    // 3. Change Subscription Notification type
    @PutMapping("/change_notification_type")
    public ResponseEntity<ApiResponseDTO> changeNotificationType(@RequestBody SubscriptionDTO dto){
        return ResponseEntity.ok().body(subscriptionService.changeNotificationType(dto));
    }

    // 4. Get User Subscription List
    @GetMapping("/get_user_subscription_list")
    public ResponseEntity<List<SubscriptionDTO>> getUserSubscriptionList(){
        return ResponseEntity.ok().body(subscriptionService.getUserSubscriptionList());
    }

    // 5. Get User Subscription List By UserId
    @GetMapping("/get_user_subscription_list_by_user_id")
    public ResponseEntity<List<SubscriptionDTO>> getUserSubscriptionListByUserId (@RequestParam("userId") String userId){
        return ResponseEntity.ok().body(subscriptionService.getUserSubscriptionListByUserId(userId));
    }
}
