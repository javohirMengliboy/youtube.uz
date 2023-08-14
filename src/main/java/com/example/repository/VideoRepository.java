package com.example.repository;

import com.example.entity.VideoEntity;
import com.example.mapper.VideoShortInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoRepository extends CrudRepository<VideoEntity, String> {
    @Query("select new com.example.mapper.VideoShortInfo(previewAttachId, title, viewCount, channelId, publishedDate) from VideoEntity where categoryId = :categoryId order by publishedDate desc")
    Page<VideoShortInfo> getPageByCategory(int categoryId, Pageable pageable);

    @Query("select new com.example.mapper.VideoShortInfo(previewAttachId, title, viewCount, channelId, publishedDate) from VideoEntity where lower(title) like :title order by publishedDate desc")
    List<VideoShortInfo> searchByTitle(String title);

    @Query("select new com.example.mapper.VideoShortInfo(v.previewAttachId, v.title, v.viewCount, v.channelId, v.publishedDate) from VideoEntity v inner join v.videoAndTagList vt where vt.tagId = :tagId order by v.publishedDate desc")
    Page<VideoShortInfo> getPageByTag(int tagId, Pageable pageable);
}
