package com.example.repository;

import com.example.dto.AttachDTO;
import com.example.entity.AttachEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachRepository extends CrudRepository<AttachEntity,String>,
        PagingAndSortingRepository<AttachEntity, String> {


    @Query("select a from AttachEntity as a where a.originalName =:origin_name")
    Optional<AttachEntity> getByName(@Param("origin_name")String origin_name);

}
