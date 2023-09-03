package com.example.repository;

import com.example.dto.ReportDTO;
import com.example.entity.ReportEntity;
import com.example.mapper.ReportInfoI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportRepository extends CrudRepository<ReportEntity, String> {
    @Query("select r.id, r.profileId, p.name, p.surname, p.photoId, r.content, r.channelId, r.videoId, r.type from ReportEntity r inner join r.profile p")
    Page<ReportInfoI> getPagination(Pageable pageable);

    @Query("select r.id, r.profileId, p.name, p.surname, p.photoId, r.content, r.channelId, r.videoId, r.type from ReportEntity r inner join r.profile p where r.profileId = :userId")
    List<ReportInfoI> getReportListByUserId(String userId);
}
