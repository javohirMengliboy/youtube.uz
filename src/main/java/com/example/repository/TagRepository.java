package com.example.repository;

import com.example.entity.TagEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Repository
public interface TagRepository extends CrudRepository<TagEntity, UUID> {
    @Transactional
    @Modifying
    @Query("update CategoryEntity as c set c.name =:name where c.id =:id")
    int updateById(@Param("id") UUID id, @Param("name")String name);
}
