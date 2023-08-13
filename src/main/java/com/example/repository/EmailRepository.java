package com.example.repository;

import com.example.entity.EmailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends CrudRepository<EmailEntity, Integer>,
        PagingAndSortingRepository<EmailEntity, Integer> {



}
