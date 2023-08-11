package com.example.repository;

import com.example.dto.AttachDTO;
import com.example.entity.AttachEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachRepository extends CrudRepository<AttachEntity,Integer>,
        PagingAndSortingRepository<AttachEntity, Integer> {


    Optional<AttachDTO> findAllByOrigin_name(String name);
}
