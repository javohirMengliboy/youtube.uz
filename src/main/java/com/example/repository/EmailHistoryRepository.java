package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Integer>,
        PagingAndSortingRepository<EmailHistoryEntity, Integer> {


    @Query("select e.message from EmailHistoryEntity as e where e.to_email =:email")
    Page<EmailHistoryEntity> pageByEmail(Pageable pageable, @Param("email")String email);

}
