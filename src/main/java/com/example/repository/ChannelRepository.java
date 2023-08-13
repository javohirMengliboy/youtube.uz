package com.example.repository;

import com.example.entity.ChannelEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ChannelRepository extends CrudRepository<ChannelEntity, String> {
    Optional<ChannelEntity> findByName(String name);
}
