package com.example.repository;

import com.example.entity.ChannelEntity;
import com.example.mapper.ChannelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ChannelRepository extends CrudRepository<ChannelEntity, String> {
    Optional<ChannelEntity> findByName(String name);

    @Query("select new com.example.mapper.ChannelMapper(name, photoId,description, status) from ChannelEntity")
    Page<ChannelMapper> pagination(Pageable pageable);
}
