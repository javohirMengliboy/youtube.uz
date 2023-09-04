package com.example.repository;

import com.example.dto.AttachDTO;
import com.example.entity.AttachEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachRepository extends CrudRepository<AttachEntity,String> {


    @Query("from AttachEntity  where originalName =:origin_name")
    Optional<AttachEntity> getByName(@Param("origin_name")String origin_name);

    @Query("from AttachEntity")
    Page<AttachEntity> getAttachPagination(Pageable pageable);
}
